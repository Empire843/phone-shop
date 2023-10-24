package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final String USER_HOME = "index";
    private static final String USER_CART = "user/cart";
    private static final String USER_CHECKOUT = "user/checkout";
    private static final String USER_PRODUCT_DETAILS = "user/product-details";
    private static final String USER_SHOP = "user/shop";
    private static final String USER_WISHLIST = "user/wishlist";

    @RequestMapping("/home")
    public String userHome() {
        return USER_HOME;
    }


}
