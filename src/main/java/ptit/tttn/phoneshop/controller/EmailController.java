package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.tttn.phoneshop.repositories.UserRepository;
import ptit.tttn.phoneshop.services.implement.EmailSenderImpl;

import java.util.logging.Logger;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderImpl emailSender;

    @GetMapping("")
    public String home() {
        return "emailSender";
    }
    @PostMapping("/send")
    public String sendEmail(@RequestParam("email") String email, @RequestParam("subject") String subject, @RequestParam("message") String message) {
        try {
            emailSender.sendEmail(email, subject, message);
            Logger.getLogger(EmailController.class.getName()).info("send email success");
        }catch (Exception e){
            Logger.getLogger(EmailController.class.getName()).info("error send email: " + e.getMessage());
            throw new RuntimeException("error send email: " + e.getMessage());
        }
        return "redirect:/email/home";
    }
}
