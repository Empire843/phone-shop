package ptit.tttn.phoneshop.services;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.Role;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.UserRepository;
import ptit.tttn.phoneshop.request.RegisterRequest;
import ptit.tttn.phoneshop.services.implement.EmailSenderImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderImpl mailSender;

    @Transactional
    public User register(@NotNull RegisterRequest request) {
        if(!checkMatchPassword(request.getPassword(), request.getPasswordConfirm())) {
            throw new RuntimeException("Password does not match");
        }
        if(checkEmailExist(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user  = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .active(false).build();
        return repo.save(user);
    }
    private boolean checkEmailExist(@NotNull String email) {
        return repo.findByEmail(email).isPresent();
    }
    private boolean checkMatchPassword(@NotNull String password, @NotNull String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
    public void sendVerificationEmail(@NotNull User user) {
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, 10);
        Date expiryDate = new Date(cal.getTime().getTime());
        user.setTokenExpirationDate(expiryDate);

        repo.save(user);
        String siteURL = "http://localhost:8080";
        String verifyURL = siteURL + "/verify?token=" + token;
        String subject = "Xác minh địa chỉ Email của bạn";
        String message = "Nhấn vào link sau để xác minh tài khoản của bạn: " + verifyURL;
        mailSender.sendEmail(user.getEmail(),subject,message);
    }
    public boolean verifyToken(String token) {
        User user = repo.findByToken(token);
        if (user != null && user.getTokenExpirationDate().after(new Date())) {
            user.setActive(true);
            user.setVerify(true);
            user.setVerifyAt(LocalDateTime.now());
            user.setToken(null); // Clear the token after verification
            user.setTokenExpirationDate(null);
            repo.save(user);
            return true;
        }
        return false;
    }
//    forget password
    public void sendResetPasswordEmail(@NotNull User user) {
        String password = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(password));
        String message = "Mật khẩu mới của bạn là: " + password+"\n" +
                "Vui lòng đăng nhập và đổi mật khẩu mới";
        mailSender.sendEmail(user.getEmail(),"Reset password account",message);
    }
}
