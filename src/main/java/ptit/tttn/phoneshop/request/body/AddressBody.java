package ptit.tttn.phoneshop.request.body;

import lombok.Data;

@Data
public class AddressBody {
    private String name;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String addressDetails;
}
