package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public  class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean active;
    private boolean verify;
    private DateTime verify_at;
    private String avatar;
    private String phone;
    private String description;
    private DateTime create_at;
    private DateTime update_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    public User(String lastName, String firstName, String username, String email, String password, Role role, boolean active, boolean verify, DateTime verify_at, String avatar, String phone, String description, DateTime create_at, DateTime update_at) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.verify = verify;
        this.verify_at = verify_at;
        this.avatar = avatar;
        this.phone = phone;
        this.description = description;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", verify=" + verify +
                ", verify_at=" + verify_at +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                '}';
    }
}
