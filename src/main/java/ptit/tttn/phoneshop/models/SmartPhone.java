package ptit.tttn.phoneshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartPhone {
    private Long id;
    private String name;
    private String urlImage;
    private String description;
    private Double price;
    private Integer quantity;
    private String brand;
    private String color;
    private String os;
    private String screen;
    private String frontCamera;
    private String rearCamera;
    private String cpu;
    private String ram;
    private String memory;
    private String sim;
    private String pin;
    private String promotion;
    private String status;
    private String urlImage1;
}
