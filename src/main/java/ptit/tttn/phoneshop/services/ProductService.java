package ptit.tttn.phoneshop.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ptit.tttn.phoneshop.models.Color;
import ptit.tttn.phoneshop.models.Image;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.ColorRepository;
import ptit.tttn.phoneshop.repositories.ProductRepository;
import ptit.tttn.phoneshop.request.body.ProductBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AwsS3Service awsS3Service;
    public List<Product> getAllProductByCategory(Long id) {
        return repo.findByCategoryId(id);
    }
    public List<Product> getRandomProduct(int limit) {
        if (limit > getAllProducts().size()) {
            return getAllProducts();
        }
        return getAllProducts().subList(0, limit);
    }
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    public List<Product> getProductByCategoryLimited(Long id, int limit) {
        if (limit > getAllProductByCategory(id).size()) {
            return getAllProductByCategory(id);
        }
        return getAllProductByCategory(id).subList(0, limit);
    }
    public List<Product> getProductByCategoryId(Long id) {
        return getAllProductByCategory(id);
    }
    public List<Product> getAllProductByCategoryAndBrand(Long cateId, String brand) {
        return repo.findByCategoryIdAndBrand(cateId, brand);
    }

    public List<Product> getAllProductByBrand(String brand) {
        return repo.findByBrand(brand);
    }

    public Product createNewProduct(ProductBody body, MultipartFile[] files) {
        Product product = Product.builder()
                .name(body.getName())
                .brand(body.getBrand())
                .price(body.getPrice())
                .operatingSystem(body.getOperatingSystem())
                .cpu(body.getCpu())
                .ram(body.getRam())
                .rom(body.getRom())
                .screenSizes(body.getScreenSizes())
                .batteryCapacity(body.getBatteryCapacity())
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
    public void updateProduct(@NotNull ProductBody body, MultipartFile[] files, Long prodId) {
        Product product = getProductById(prodId);
        Logger logger = Logger.getLogger(ProductService.class.getName());

//        body.getColors().forEach(color -> {
//            logger.info(color);
//        });
        product.setName(body.getName());
        product.setBrand(body.getBrand());
        product.setPrice(body.getPrice());
        product.setOperatingSystem(body.getOperatingSystem());
        product.setCpu(body.getCpu());
        product.setRam(body.getRam());
        product.setRom(body.getRom());
        product.setScreenSizes(body.getScreenSizes());
        product.setBatteryCapacity(body.getBatteryCapacity());
        product.setDescription(body.getDescription());
        product.setUpdateAt(LocalDateTime.now());
        product.setCategory(categoryService.getById(body.getCategoryId()));
        List<Color> colors = createProductColor(product, body.getColors());
        List<Image> images = createProductImage(product, files);
        List<Image> imagesUpdate = updateProductImage(product, body.getImages());
        product.getColors().clear();
        product.getColors().addAll(colors);

        product.getImages().clear();
        product.getImages().addAll(imagesUpdate);
        product.getImages().addAll(images);
        repo.save(product);
    }

    private List<Image> createProductImage(Product product, MultipartFile[] files) {
        List<String> imageList = awsS3Service.uploadMultipleFile(files, "images");
        List<Image> images = new ArrayList<>();
        for (String image : imageList) {
            if (image == null || image.isEmpty()) {
                continue;
            }
            Image i = Image.builder()
                    .urlImage(image)
                    .create_at(LocalDateTime.now())
                    .update_at(LocalDateTime.now())
                    .product(product)
                    .build();
            images.add(i);
        }
        return images;
    }

    private List<Color> createProductColor(Product product, List<String> colors) {
        List<Color> colorList = new ArrayList<>();
        for (String color : colors) {
            if (color == null || color.isEmpty()) {
                continue;
            }
            Color c = Color.builder()
                    .colorName(color)
                    .create_at(LocalDateTime.now())
                    .update_at(LocalDateTime.now())
                    .product(product)
                    .build();
            colorList.add(c);
        }
        return colorList;
    }
    private List<Image> updateProductImage(Product product, List<Integer> imageIds) {
        List<Image> imageList = new ArrayList<>();
        List<Image> images = product.getImages();
        for (Integer id : imageIds) {
            if(id == null){
                continue;
            }
            images.forEach(image -> {
                if (image.getId() == Integer.parseInt(String.valueOf(id))) {
                    imageList.add(image);
                }
            });
        }
        return imageList;
    }
    public List<Product> getAllProduct() {
        return repo.findAll();
    }

    public void deleteProduct(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new RuntimeException("product not found!");
        }
        repo.deleteById(id);
    }

    public Product getProductById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("product not found!"));
    }
    public List<Product> searchProductByName(String name) {
        return repo.findByNameContaining(name);
    }

    public List<Product> searchProduct(String search) {
        return repo.findByNameContaining(search);
    }
}
