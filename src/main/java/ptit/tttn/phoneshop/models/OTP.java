package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Entity
@Table(name = "otp")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String otp;
    private boolean active;
    private DateTime create_at;
    private DateTime update_at;
    private DateTime expired_at;

    @Override
    public String toString() {
        return "OTP{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                ", active=" + active +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                ", expired_at=" + expired_at +
                '}';
    }
}