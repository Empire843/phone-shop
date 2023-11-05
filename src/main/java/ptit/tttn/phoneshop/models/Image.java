package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "images")
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlImage;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private DateTime create_at;
    private DateTime update_at;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", urlImage='" + urlImage + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                '}';
    }
}
