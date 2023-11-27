package ptit.tttn.phoneshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.tttn.phoneshop.models.Cart;
import ptit.tttn.phoneshop.models.CartItem;
import ptit.tttn.phoneshop.models.Product;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    CartItem findByProductId(Long id);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
