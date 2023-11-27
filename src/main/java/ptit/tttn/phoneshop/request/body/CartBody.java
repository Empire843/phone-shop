package ptit.tttn.phoneshop.request.body;

import lombok.Data;

@Data
public class CartBody {
    private Long userId;
    private Long productId;
    private int quantity;
    private String colorName;
}
