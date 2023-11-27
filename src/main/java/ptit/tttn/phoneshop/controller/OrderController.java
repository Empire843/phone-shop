package ptit.tttn.phoneshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.tttn.phoneshop.models.CartItem;
import ptit.tttn.phoneshop.models.Order;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ProductService productService;

    @Autowired
    private DeliveryAddressService addressService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemService cartItemService;

    private static final String ORDER_PROCESS_PAGE = "user/order-process";

    @GetMapping("")
    public String order(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            return "redirect:/login";
        try{
            User user = userService.getUserByUsername(authentication.getName());
            HttpSession session = request.getSession();
            List<String> selectedItems = (List<String>) session.getAttribute("selectedItems");

            List<CartItem> cartItems = new ArrayList<>();
            selectedItems.forEach(id ->  cartItems.add(cartItemService.getCartItemById(Long.parseLong(id))));
            model.addAttribute("addresses", addressService.getAllAddress());
            model.addAttribute("user", user);
            model.addAttribute("cartItems", cartItems);

            Double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
            model.addAttribute("totalPrice", totalPrice);
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cart";
        }
        return ORDER_PROCESS_PAGE;
    }

    @PostMapping("/order-item-selected")
    public String prepareOrder(@RequestParam List<String> selectedItems, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("selectedItems", selectedItems);
        return "redirect:/order";
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam List<String> itemIds, @RequestParam String addressId, @RequestParam String paymentMethod, RedirectAttributes redirectAttributes) {
        Logger logger = Logger.getLogger("OrderController");
        logger.info("itemIds: " + itemIds);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            return "redirect:/login";

        User user = userService.getUserByUsername(authentication.getName());
        Order order = orderService.createOrder(itemIds, user, addressId);
        return "redirect:/order/order-success";
    }
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable("id") String id, Model model){
        Order order = orderService.getOrderById(Long.parseLong(id));
        model.addAttribute("order", order);
        return "user/order-detail";
    }
    @GetMapping("order-success")
    public String orderSuccess(){
        return "user/order-success";
    }


}
