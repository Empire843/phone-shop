package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("title", "Spring Boot x Tailwind CSS");
        return "home";
    }
}
