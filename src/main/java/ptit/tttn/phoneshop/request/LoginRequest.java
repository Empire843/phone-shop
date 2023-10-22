package ptit.tttn.phoneshop.request;

import lombok.Data;

import javax.xml.parsers.SAXParser;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
