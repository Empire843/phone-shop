package ptit.tttn.phoneshop.request.body;

import lombok.Data;

import java.util.List;
@Data
public class CartItemSelectedBody {
    private Long userId;
    private List<Long> listProductId;
}
