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

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public User register(@NotNull RegisterRequest request) {
        if(!checkMatchPassword(request.getPassword(), request.getPasswordConfirm())) {
            throw new RuntimeException("Password does not match");
        }
        User user  = User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .role(Role.USER)
                        .active(true).build();
        return repo.save(user);
    }
    private boolean checkEmailExist(@NotNull String email) {
        return repo.findByEmail(email).isPresent();
    }
    private boolean checkMatchPassword(@NotNull String password, @NotNull String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
    public boolean isAccountActivated(String email) {
        User user = (User) repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return user.isActive();
    }

    public Optional<Object> getByUsername(String username) {
        return repo.findByUsername(username);
    }

    public void saveUser(User user) {
        repo.save(user);
    }
}
