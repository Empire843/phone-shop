package ptit.tttn.phoneshop.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.UserRepository;
import ptit.tttn.phoneshop.request.RegisterRequest;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public User register(RegisterRequest registerRequest) {
        if(checkUsernameExist(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if(checkEmailExist(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if(!checkMatchPassword(registerRequest.getPassword(), registerRequest.getPasswordConfirm())) {
            throw new RuntimeException("Password does not match");
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();
        return repo.save(user);
    }
    private boolean checkUsernameExist(@NotNull String username)  {
        return repo.findByUsername(username).isPresent();
    }
    private boolean checkEmailExist(@NotNull String email) {
        return repo.findByEmail(email).isPresent();
    }
    private boolean checkMatchPassword(@NotNull String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
}
