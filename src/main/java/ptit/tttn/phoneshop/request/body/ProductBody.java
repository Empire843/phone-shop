package ptit.tttn.phoneshop.request.body;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductBody {
    private String name;
    private String brand;
    private String description;
    private Double price;
    private Long categoryId;
    List<String> colors = new ArrayList<>();
}
