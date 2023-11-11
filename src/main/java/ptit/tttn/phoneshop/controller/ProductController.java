package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping("")
    public String product() {
        return "user/product";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") long id) {
        return "user/product-details";
    }
    // add new product
    @GetMapping("/add")
    public String addToCart() {
        return "product-form";
    }
}