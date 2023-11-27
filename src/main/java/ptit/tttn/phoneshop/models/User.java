package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private LocalDateTime verifyAt;
    private String avatar;
    private String phone;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpirationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
    public User(String lastName, String firstName, String username, String email, String password, Role role, boolean active, boolean verify, LocalDateTime verifyAt, String avatar, String phone, String description, LocalDateTime createAt, LocalDateTime updateAt) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.verify = verify;
        this.verifyAt = verifyAt;
        this.avatar = avatar;
        this.phone = phone;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", verify=" + verify +
                ", verify_at=" + verifyAt +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", create_at=" + createAt +
                ", update_at=" + updateAt +
                '}';
    }
}
