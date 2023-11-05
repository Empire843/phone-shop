package ptit.tttn.phoneshop.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.services.EmailSenderService;

import java.util.UUID;


@Service
public class EmailSenderImpl implements EmailSenderService {
    @Autowired
    private  JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        String token = UUID.randomUUID().toString();
//        verificationTokenRepository.save(new VerificationToken(user, token));
//
//        // Tạo URL xác minh
//        String verificationUrl = "http://yourdomain.com/verify?token=" + token;
//
//        // Tạo nội dung email
//        String subject = "Xác minh tài khoản của bạn";
//        String text = "Để xác minh tài khoản của bạn, vui lòng nhấp vào link sau: " + verificationUrl;
//
//        // Gửi email
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("kien.quachdinh.work@gmail.com");
//        message.setTo(user.getEmail());
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
    }
}
