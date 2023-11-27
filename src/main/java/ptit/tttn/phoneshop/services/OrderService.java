package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.*;
import ptit.tttn.phoneshop.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DeliveryAddressService addressService;
    public Order createOrder(List<String> cartItemIds, User user, String addressId) {
        DeliveryAddress address = addressService.getById(Long.parseLong(addressId));
        Order order = Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .create_at(LocalDateTime.now())
                .update_at(LocalDateTime.now())
                .user(user)
                .orderDate(LocalDateTime.now())
                .deliveryAddress(address)
                .deliveryDate(LocalDateTime.now().plusDays(7))
                .total(0.0)
                .build();
        order = repository.save(order);
        order.setOrderItems(orderItemService.createOrderItem(cartItemIds, order));
        order.setTotal(order.getOrderItems().stream().mapToDouble(OrderItem::getPriceAtPurchase).sum());
        return repository.save(order);
    }

    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrderByUser(User user) {
        return repository.findAllByUser(user);
    }

    public List<Order> getAllOrder() {
        return repository.findAll();
    }

    public List<Order> searchOrderByUser(String searchKeyWord) {
//        search by lastname + first name
        return repository.findAllByUser_LastNameContainingOrUser_FirstNameContaining(searchKeyWord, searchKeyWord);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
