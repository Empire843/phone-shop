package ptit.tttn.phoneshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.tttn.phoneshop.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
