package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.tttn.phoneshop.request.RegisterRequest;
import ptit.tttn.phoneshop.services.UserService;

@Controller
@RequestMapping("/")
public class AccountController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest, Model model) {
        try{
            userService.register(registerRequest);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Đăng ký thất bại: "+e.getMessage());
            return "error/errorPage";
        }
        return "register";
    }
    @PostMapping("/login")
    public String login() {
        return "login";
    }
}
