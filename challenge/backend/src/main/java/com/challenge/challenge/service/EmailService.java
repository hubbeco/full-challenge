package com.challenge.challenge.service;

import com.challenge.challenge.model.ContactForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.json.JSONObject;
import com.challenge.challenge.exception.BadRequestException;
import com.challenge.challenge.exception.UnauthorizedErrorException;
import com.challenge.challenge.exception.InternalServerErrorException;

import java.util.regex.Pattern;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.text.mail.title}")
    private String mailSubject;

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${company.email}")
    private String companyEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean verifyRecaptcha(String recaptchaToken) {
        if (recaptchaToken == null || recaptchaToken.isEmpty()) {
            logger.error("reCAPTCHA token is missing");
            return false;
        }

        String url = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "secret=" + recaptchaSecret + "&response=" + recaptchaToken;
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            logger.info("reCAPTCHA response: {}", jsonResponse.toString()); // Adiciona log da resposta completa
            boolean success = jsonResponse.getBoolean("success");
            if (!success) {
                logger.error("reCAPTCHA verification failed: {}", jsonResponse.toString());
            }
            return success;
        } else {
            logger.error("Failed to verify reCAPTCHA: HTTP error code {}", response.getStatusCode());
            return false;
        }
    }

    public void sendEmail(ContactForm form, String recaptchaToken) {
        try {
            if (!isValidForm(form)) {
                if (!isValidEmail(form.getMail())) {
                    throw new BadRequestException("The email is invalid");
                }
                if (!isValidName(form.getName())) {
                    throw new BadRequestException("The name is empty");
                }
            }
            if (recaptchaToken == null || recaptchaToken.isEmpty() || !verifyRecaptcha(recaptchaToken)) {
                throw new UnauthorizedErrorException("The captcha is incorrect!", "/api/contact");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(form.getMail());
            message.setCc(companyEmail); // Adiciona o email da empresa como cópia
            message.setSubject(mailSubject);
            message.setText(form.getComment());

            mailSender.send(message);
            logger.info("Email sent successfully to {} and {}", form.getMail(), companyEmail);
        } catch (BadRequestException | UnauthorizedErrorException e) {
            logger.error("Error sending email: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            throw new InternalServerErrorException("Some generic error name.");
        }
    }

    public boolean isValidForm(ContactForm form) {
        return isValidEmail(form.getMail()) && isValidComment(form.getComment());
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean isValidComment(String comment) {
        return comment != null && !comment.trim().isEmpty();
    }
}
