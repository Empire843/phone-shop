package ptit.tttn.phoneshop.tools;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ptit.tttn.phoneshop.models.Role;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.UserRepository;
import ptit.tttn.phoneshop.services.UserService;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(repo.findByUsername("admin").isEmpty()) {
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@gmail.com")
                    .firstName("Admin")
                    .role(Role.ADMIN)
                    .active(true)
                    .verify(true)
//                    .create_at(DateTime.now())
//                    .update_at(DateTime.now())
//                    .verify_at(DateTime.now())
                    .description("Admin")
                    .build();
            repo.save(user);
        }
    }
}
