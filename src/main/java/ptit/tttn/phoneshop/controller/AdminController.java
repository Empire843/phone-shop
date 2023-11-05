package ptit.tttn.phoneshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String ADMIN_DASHBOARD = "admin/dashboard";
    @RequestMapping(value = {"/","", "/dashboard"})
    public String dashboard() {
        return ADMIN_DASHBOARD;
    }
}
