package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
