package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String consigneeName;
    private String addressDetails;
    private String city;
    private String district;
    private String ward;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @OneToMany(mappedBy = "deliveryAddress")
    private List<Order> orders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                '}';
    }
    public String getFullAddress() {
        return this.addressDetails + ", " + this.ward + ", " + this.district + ", " + this.city;
    }
}
