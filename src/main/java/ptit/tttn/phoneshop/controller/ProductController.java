package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final String PRODUCT_HOME = "product/home";
    private static final String PRODUCT_CART = "product/cart";
    private static final String PRODUCT_CHECKOUT = "product/checkout";
    private static final String PRODUCT_PRODUCT_DETAILS = "product/product-details";
    private static final String PRODUCT_SHOP = "product/shop";
    private static final String PRODUCT_WISHLIST = "product/wishlist";

}
