package ptit.tttn.phoneshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ptit.tttn.phoneshop"})
public class PhoneShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhoneShopApplication.class, args);
    }
}
