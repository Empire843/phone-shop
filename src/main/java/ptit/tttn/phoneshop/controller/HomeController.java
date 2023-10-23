package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final String USER_HOME = "user/home";

    @GetMapping(value = {"/", "home"})
    public String indexPage(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "index";
    }
    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "login";
    }
    @GetMapping("register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "register";
    }
    @GetMapping("edit-product")
    public String updateProductPage(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "admin/edit-product";
    }
    @GetMapping("product-detail")
    public String productDetails(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "user/product-details";
    }
}
