package ptit.tttn.phoneshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.services.ProductService;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    //api search product by name
//    @GetMapping("/search")
//    public ResponseEntity<Object> searchProductByName(@RequestParam("name") String name) {
//        List<Product> products = productService.searchProductByName(name);
//        if(products.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        HashMap<String, Object> response = new HashMap<>();
//        response.put("products", products);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}

