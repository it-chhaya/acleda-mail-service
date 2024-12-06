package co.istad.email.config;

import co.istad.email.MailRequest;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
@RequiredArgsConstructor
public class MailServerConfig {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminMail;

    public void send(MailRequest<?> mailRequest) {
        // TODO
        // 1. Create Message Object
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 2. Set Message Object Information
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(mailRequest.to());
            mimeMessageHelper.setFrom(adminMail);
            mimeMessageHelper.setCc(mailRequest.cc());
            mimeMessageHelper.setSubject(mailRequest.subject());
            mimeMessageHelper.setText(mailRequest.template());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error send mail: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
