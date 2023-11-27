package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                '}';
    }
    public Double getTotalPrice(){
        return cartItems.stream().mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum();
    }
    public Long getTotalQuantity(){
        return cartItems.stream().mapToLong(CartItem::getQuantity).sum();
    }
}
