package com.challenge.challenge.service;

import com.challenge.challenge.model.ContactForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.text.mail.title}")
    private String mailSubject;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(ContactForm form) {
        try {
            if (!isValidForm(form)) {
                throw new IllegalArgumentException("Invalid form data");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(form.getMail());
            message.setSubject(mailSubject);
            message.setText(form.getComment());

            mailSender.send(message);
            logger.info("Email sent successfully to {}", form.getMail());
        } catch (Exception e) {
            logger.error("Error sending email: {}", e.getMessage());
            throw e;
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

    public boolean isValidRecaptcha(String recaptchaResponse) {
        return recaptchaResponse != null && !recaptchaResponse.trim().isEmpty();
    }
}
