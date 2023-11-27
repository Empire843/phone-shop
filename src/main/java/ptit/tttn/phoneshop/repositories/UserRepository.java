package ptit.tttn.phoneshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.tttn.phoneshop.models.Role;
import ptit.tttn.phoneshop.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String email);
    Optional<Object> findByUsername(String username);
    User findByToken(String token);

    Optional<User> findByEmailAndUsername(String email, String username);

    List<User> findAllByRole(Role role);
}
