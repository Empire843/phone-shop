package ptit.tttn.phoneshop.request.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.tttn.phoneshop.models.Image;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductBody {
    private String name;
    private String brand;
    private String description;
    private int ram;
    private int rom;
    private float screenSizes;
    private String cpu;
    private int batteryCapacity;
    private String operatingSystem;
    private Long categoryId;
    private Double price;
    List<String> colors = new ArrayList<>();
    List<Integer> images = new ArrayList<>();
}
