package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.Cart;
import ptit.tttn.phoneshop.models.CartItem;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.CartItemRepository;
import ptit.tttn.phoneshop.repositories.CartRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Assuming you have a method to get the cart by user
    public Cart getCartByUser(User user) {
        // Method implementation to retrieve cart
        return cartRepo.findByUser(user);
    }

    public void addProductToCart(User user, Product product, int quantity, String colorName) {
        Cart cart = getCartByUser(user);
        if(cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCreate_at(LocalDateTime.now());
            cart.setUpdate_at(LocalDateTime.now());
            cartRepo.save(cart);
        }
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem.isPresent() && existingCartItem.get().getColorName().equals(colorName)) {
            // Product already in the cart, increase quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setUpdate_at(LocalDateTime.now());
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Product not in the cart, add new
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .colorName(colorName)
                    .create_at(LocalDateTime.now())
                    .update_at(LocalDateTime.now())
                    .build();
            cartItemRepository.save(cartItem);
        }
    }

    public void deleteProductFromCart(User user, Product product) {
        Cart cart = getCartByUser(user);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItemRepository.delete(cartItem);
            return;
        }
        throw new RuntimeException("Cart item not found");
    }

    public CartItem updateProductInCart(User user, Product product, int quantity) {
        Cart cart = getCartByUser(user);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            if(cartItem.getQuantity() > 0) {
                return cartItemRepository.save(cartItem);
            }else{
                throw new RuntimeException("Quantity must be greater than 0");
            }
        }
        throw new RuntimeException("Cart item not found");
    }
}
