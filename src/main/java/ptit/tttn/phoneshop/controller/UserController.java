package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.tttn.phoneshop.models.Order;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.services.OrderService;
import ptit.tttn.phoneshop.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final String USER_HOME = "index";
    private static final String USER_CART = "user/cart";
    private static final String USER_CHECKOUT = "user/checkout";
    private static final String USER_PRODUCT_DETAILS = "user/product-details";
    private static final String USER_SHOP = "user/shop";
    private static final String USER_WISHLIST = "user/wishlist";

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @GetMapping("/change-password")
    public String changePassword() {
        return "user/change-password";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @PostMapping("/profile-update")
    public String profileUpdate(Model model) {
        model.addAttribute("message", "Cập nhật thành công");
        return "user/profile";
    }

    @GetMapping("/order-history")
    public String orderHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        List<Order> listOrder = orderService.getAllOrderByUser(user);
        model.addAttribute("listOrder", listOrder);
        return "user/order-history";
    }
}
