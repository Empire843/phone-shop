package ptit.tttn.phoneshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.tttn.phoneshop.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
