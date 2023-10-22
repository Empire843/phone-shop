package ptit.tttn.phoneshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private String name;
private String address;
private String phone;
private String email;
private String note;
private String payment;
private String status;
private String date;
private String total;
private String code;
private String userId;
private String username;
private String userAddress;
private String userPhone;

}
