package ptit.tttn.phoneshop.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.services.EmailSenderService;

import java.util.Properties;

@Service
public class EmailSenderImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

//    public EmailSenderImpl() {
//        this.emailSender = getJavaMailSender();
//    }

//    private JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("kien.quachdinh.work@gmail.com");
//        mailSender.setPassword("nozklcsktkegburm");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        return mailSender;
//    }
    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kien.quachdinh.work@gmail");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
