package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.tttn.phoneshop.models.Category;
import ptit.tttn.phoneshop.request.RegisterRequest;
import ptit.tttn.phoneshop.services.CategoryService;
import ptit.tttn.phoneshop.services.ProductService;
import ptit.tttn.phoneshop.services.UserService;
import ptit.tttn.phoneshop.services.implement.EmailSenderImpl;

import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    private static final String LOGIN_PAGE = "common/login";
    private static final String REGISTER_PAGE = "common/register";
    private static final String HOME_PAGE = "user/home";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EmailSenderImpl emailSender;
    @GetMapping(value = {"/", "/home", "/index"})
    public String home(Model model, RedirectAttributes redirectAttributes) {
        HashMap<Object, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ("ADMIN".equals(auth.getAuthorities().toArray()[0].toString())) {
            return "redirect:/admin/dashboard";
        }
        List<Category> categories = categoryService.getAllCategory();
        for (int i = 0; i < Math.min(3, categories.size()); i++) {
            Category category = categories.get(i);
            response.put(category, productService.getProductByCategoryLimited(category.getId(), 4));
        }
        model.addAttribute("response", response);
        return HOME_PAGE;
    }
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if ("ADMIN".equals(authentication.getAuthorities().toArray()[0].toString())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
        }
        return LOGIN_PAGE;
    }
    @GetMapping("/register")
    public String register(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if ("ADMIN".equals(authentication.getAuthorities().toArray()[0].toString())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
        }
        return REGISTER_PAGE;
    }
    @PostMapping("/register")
    public String registerProcess(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String passwordConfirm,
            @RequestParam String firstName,
            @RequestParam String lastName,
            Model model) {
        RegisterRequest registerRequest = new RegisterRequest(username, email, password, passwordConfirm, null, firstName, lastName);
        try{
            ptit.tttn.phoneshop.models.User user = userService.register(registerRequest);
            userService.sendVerificationEmail(user);
//            model.addAttribute("message", "Đăng ký thành công");
            model.addAttribute("user", user);
            return "redirect:/confirm";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Đăng ký thất bại: "+e.getMessage());
            return "redirect:/register";
        }
    }
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "common/forgot_password";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, @RequestParam("username") String username,
                                 RedirectAttributes redirectAttributes, Model model) {
        if (email != null && username != null) {
            try {
                userService.sendResetPasswordEmail(email, username);
                model.addAttribute("message", "Send email successfully");
                redirectAttributes.addFlashAttribute("message", "Send email successfully");
            } catch (RuntimeException e) {
                model.addAttribute("error", "Send email failed: "+e.getMessage());
                redirectAttributes.addFlashAttribute("error", "Send email failed: "+e.getMessage());
            }
        }
        return "redirect:/forgot-password";
    }
    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token, RedirectAttributes redir) {
        boolean verified = userService.verifyToken(token);
        if (verified) {
            redir.addFlashAttribute("message", "Verify successfully");
            return "redirect:/login";
        } else {
            redir.addFlashAttribute("errorMessage", "Verify failed");
            return "redirect:/confirm";
        }
    }

    @GetMapping("confirm")
    public String verify() {
        return "common/confirm_email";
    }
}