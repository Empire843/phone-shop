package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String username;
    private String password;
    private String phone;
    @OneToMany(mappedBy = "user")
    private List<Address> address = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;
    private String urlImage;
}
