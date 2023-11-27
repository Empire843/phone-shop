package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.tttn.phoneshop.models.Cart;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.services.CartService;
import ptit.tttn.phoneshop.services.ProductService;
import ptit.tttn.phoneshop.services.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            return "redirect:/login";

        User user = userService.getUserByUsername(authentication.getName());
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        model.addAttribute("userId", user.getId());
        return "user/cart";
    }

}
