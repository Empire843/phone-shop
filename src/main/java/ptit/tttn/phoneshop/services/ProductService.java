package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ptit.tttn.phoneshop.models.Color;
import ptit.tttn.phoneshop.models.Image;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.repositories.ProductRepository;
import ptit.tttn.phoneshop.request.body.ProductBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AwsS3Service awsS3Service;

    public Product createNewProduct(ProductBody body, MultipartFile[] files) {
        Product product = Product.builder()
                .name(body.getName())
                .brand(body.getBrand())
                .price(body.getPrice())
                .description(body.getDescription())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .category(categoryService.getById(body.getCategoryId()))
                .build();
        Product product1 = repo.save(product);

        List<Color> colors = createProductColor(product1, body.getColors());
        List<Image> images = createProductImage(product1, files);

        product1.setColors(colors);
        product1.setImages(images);
        return repo.save(product1);
    }
    private List<Image> createProductImage(Product product, MultipartFile[] files) {
        List<String> imageList = awsS3Service.uploadMultipleFile(files, "images");
        List<Image> images = new ArrayList<>();
        for (String image : imageList) {
            Image i = Image.builder()
                    .urlImage(image)
                    .product(product)
                    .build();
            images.add(i);
        }
        return images;
    }

    private List<Color> createProductColor(Product product, List<String> colors) {
        List<Color> colorList = new ArrayList<>();
        for (String color : colors) {
            Color c = Color.builder()
                    .colorName(color)
                    .product(product)
                    .build();
            colorList.add(c);
        }
        return colorList;
    }

    public List<Product> getAllProduct() {
        return repo.findAll();
    }
}
