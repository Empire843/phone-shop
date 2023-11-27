package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.models.Review;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.services.ProductService;
import ptit.tttn.phoneshop.services.ReviewService;
import ptit.tttn.phoneshop.services.UserService;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;
    @GetMapping("")
    public String product() {
        return "user/product";
    }
    @GetMapping("/category/{id}")
    public String productByCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("products", productService.getProductByCategoryId(id));
        return "redirect:/user/shop";
    }
    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Assuming you have a method to get the user ID by username
        Long userId = 0L;
        try{
            User user = userService.getUserByUsername(username);
            userId = user.getId();
        }catch (Exception e){
            userId = 0L;
        }
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("userId", userId);
        model.addAttribute("reviews", reviewService.getAllByProductId(id));
        Logger.getLogger("ProductController").info("Review: " +  reviewService.getAllByProductId(id));
        return "user/product-details";
    }
    // add new product
    @PostMapping("/add-review")
    public String addReview(@RequestParam("productId") long productId,
                            @RequestParam("rate") int rate,
                            @RequestParam("comment") String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            User user = userService.getUserByUsername(authentication.getName());
            Product product = productService.getProductById(productId);
            Review review = Review.builder()
                    .rate(rate)
                    .comment(comment)
                    .create_at(LocalDateTime.now())
                    .update_at(LocalDateTime.now())
                    .user(user)
                    .product(product).build();
            reviewService.create(review);
        }catch (Exception e){
            return "redirect:/login";
        }
        return "redirect:/product/" + productId;
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("search") String search, Model model) {
        model.addAttribute("products", productService.searchProduct(search));
        return "user/product-list";
    }
    @GetMapping("/view-by-category/{id}")
    public String viewByCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("products", productService.getProductByCategoryId(id));
        return "user/product-list";
    }
}