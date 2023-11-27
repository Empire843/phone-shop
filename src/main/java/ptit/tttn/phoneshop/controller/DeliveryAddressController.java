package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.tttn.phoneshop.models.DeliveryAddress;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.request.body.AddressBody;
import ptit.tttn.phoneshop.services.DeliveryAddressService;
import ptit.tttn.phoneshop.services.UserService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/delivery-address")
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService addressService;

    @Autowired
    private UserService userService;
    @PostMapping("add-address")
    public String deliveryAddress(@ModelAttribute("address") AddressBody address, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            return "redirect:/login";

        User user = userService.getUserByUsername(authentication.getName());
        Logger logger = Logger.getLogger("Address");
        logger.info(address.toString());

        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .addressDetails(address.getAddressDetails())
                .consigneeName(address.getName())
                .phone(address.getPhone())
                .city(address.getCity())
                .district(address.getDistrict())
                .ward(address.getWard())
                .user(user)
                .build();
        addressService.save(deliveryAddress);
        return "redirect:/order";
    }
}
