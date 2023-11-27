package ptit.tttn.phoneshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ptit.tttn.phoneshop.models.Cart;
import ptit.tttn.phoneshop.models.CartItem;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.request.body.CartBody;
import ptit.tttn.phoneshop.request.body.CartItemSelectedBody;
import ptit.tttn.phoneshop.services.CartItemService;
import ptit.tttn.phoneshop.services.CartService;
import ptit.tttn.phoneshop.services.ProductService;
import ptit.tttn.phoneshop.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/api/cart")
public class CartRestController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

//    api get totalPrice of cartItem in cart
    @PostMapping("/total-price-item")
    public ResponseEntity<Object> getTotalPriceItem(@RequestBody CartItemSelectedBody body) {
        try {
            HashMap<String, Object> response = new HashMap<>();
            User user = userService.getUserById(body.getUserId());
            Cart cart = cartService.getCartByUser(user);
            List<CartItem> cartItems = cart.getCartItems();
            List<Long> productIds = body.getListProductId();
//            calculate total price of cartItem by list productId
            double totalPriceItemSelected = 0;
            for (CartItem cartItem : cartItems) {
                if (productIds.contains(cartItem.getProduct().getId())) {
                    totalPriceItemSelected += cartItem.getTotalPrice();
                }
            }
            response.put("totalPriceItemSelected", totalPriceItemSelected);
            response.put("message", "Get total price of cartItem successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/add-item")
    public ResponseEntity<Object> addProductToCart(@RequestBody CartBody body) {
        try {
            User user = userService.getUserById(body.getUserId());
            Product product = productService.getProductById(body.getProductId());
            cartService.addProductToCart(user, product, body.getQuantity(), body.getColorName());

            return ResponseEntity.ok("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-item")
    public ResponseEntity<Object> deleteProductFromCart(@RequestBody CartBody body) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserById(body.getUserId());
            Product product = productService.getProductById(body.getProductId());
            cartService.deleteProductFromCart(user, product);
            response.put("totalPrice", cartService.getCartByUser(user).getTotalPrice());
            response.put("message", "Product deleted from cart successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/update-item")
    public ResponseEntity<Object> updateProductInCart(@RequestBody CartBody body) {
        try {
            HashMap<String, Object> response = new HashMap<>();
            User user = userService.getUserById(body.getUserId());
            Product product = productService.getProductById(body.getProductId());
            CartItem cartItem = (cartService.updateProductInCart(user, product, body.getQuantity()));
            response.put("quantity", cartItem.getQuantity());
            response.put("totalPrice", cartService.getCartByUser(user).getTotalPrice());
            response.put("totalPriceItem", cartItem.getTotalPrice());

            response.put("message", "Product upsdated in cart successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
     // api delete product in cart

    // api delete all product in cart
}
