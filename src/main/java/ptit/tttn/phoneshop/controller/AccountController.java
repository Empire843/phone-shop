package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.request.RegisterRequest;
import ptit.tttn.phoneshop.services.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public  String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String passwordConfirm,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           Model model) {
        RegisterRequest registerRequest = new RegisterRequest(username, email, password, passwordConfirm, null, firstName, lastName);
        try{
            User user = userService.register(registerRequest);
            model.addAttribute("message", "Đăng ký thành công");
            model.addAttribute("user", user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Đăng ký thất bại: "+e.getMessage());
            return "error/errorPage";
        }
        return "login";
    }
    @PostMapping("/login")
    public String login() {
        return "login";
    }
}
