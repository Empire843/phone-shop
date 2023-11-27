package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.*;
import ptit.tttn.phoneshop.repositories.CartItemRepository;
import ptit.tttn.phoneshop.repositories.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository repo;
    @Autowired
    private CartItemService cartItemService;

    public List<OrderItem> createOrderItem(List<String> cartItemIds, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = new ArrayList<>();
        for (String id : cartItemIds) {
            CartItem cartItem = cartItemService.getCartItemById(Long.parseLong(id));
            cartItems.add(cartItem);
            OrderItem orderItem = OrderItem.builder()
                    .create_at(LocalDateTime.now())
                    .update_at(LocalDateTime.now())
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .colorName(cartItem.getColorName())
                    .priceAtPurchase(cartItem.getProduct().getPrice())
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        repo.saveAll(orderItems);
        cartItemService.deleteCartItem(cartItems);
        return orderItems;
    }

}
