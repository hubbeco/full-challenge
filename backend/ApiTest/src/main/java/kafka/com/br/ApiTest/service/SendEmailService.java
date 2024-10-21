package kafka.com.br.ApiTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${text.mail.title}")
    private String title;

    @Value("${text.mail.body}")
    private String body;

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendingEmail(String to, String comment, String mail){
        try {

            body = body.replace("{", "")
                    .replace("}", "")
                    .replace("name", to)
                    .replace("comment", comment);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(mail);
            message.setSubject(title);
            message.setText(body);
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}
