package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.Cart;
import ptit.tttn.phoneshop.models.CartItem;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.repositories.CartItemRepository;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public  CartItem getCartItemByProductId(Long id) {
        return cartItemRepository.findByProductId(id);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem getCartItemByCartAndProduct(Cart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product).orElseThrow(() -> new RuntimeException("Cart item not found"));
    }

    public CartItem getCartItemById(long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart item not found"));
    }

    public void deleteCartItem(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }
}
