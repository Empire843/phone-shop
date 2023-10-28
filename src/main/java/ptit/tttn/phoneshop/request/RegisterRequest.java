package ptit.tttn.phoneshop.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
    private String phone;
    private String firstName;
    private String lastName;
}
