package ptit.tttn.phoneshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "images")
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlImage;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

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
