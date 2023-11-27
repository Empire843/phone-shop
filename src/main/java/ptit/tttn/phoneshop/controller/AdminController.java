package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.tttn.phoneshop.models.*;
import ptit.tttn.phoneshop.repositories.CategoryRepository;
import ptit.tttn.phoneshop.request.body.ProductBody;
import ptit.tttn.phoneshop.services.OrderService;
import ptit.tttn.phoneshop.services.ProductService;
import ptit.tttn.phoneshop.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository cateRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    private static final String ADMIN_DASHBOARD = "admin/dashboard";
    @GetMapping(value = {"/","", "/dashboard"})
    public String dashboard(Model model) {
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return ADMIN_DASHBOARD;
    }
    @GetMapping("/product-management")
    public String productManagement(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        System.out.println(products);
        return "admin/product-management";
    }
    @GetMapping("/product-management/add-product")
    public String addnewProduct(Model model) {
        List<Category> categories = cateRepo.findAll();
        model.addAttribute("categories", categories);
        return "admin/product-form";
    }
    @PostMapping("/product-management/add-product")
    public String addNewProduct(@ModelAttribute("product") ProductBody product,
                                @RequestParam("files") MultipartFile[] files,
                                Model model, RedirectAttributes redirectAttributes) {

        productService.createNewProduct(product, files);
        redirectAttributes.addFlashAttribute("message", "Add new product successfully");
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "redirect:/admin/product-management";
    }
    @GetMapping("/product-management/update/{id}")
    public String updateProdPage(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = cateRepo.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/product-form";
    }

    @PostMapping("/product-management/update/{id}")
    public String updateprod(@ModelAttribute("product") ProductBody product,
                             @RequestParam("files") MultipartFile[] files,
                             @RequestParam("id") Long prodId) {
        Logger logger = Logger.getLogger(AdminController.class.getName());
        product.getImages().forEach(image -> {
            logger.info(image + "\n");
        });
        productService.updateProduct(product, files, prodId);
        return "redirect:/admin/product-management";
    }
    @DeleteMapping("/product-management/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Product deleted failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//category-management
    @GetMapping("/category-management")
    public String cateManagement(Model model) {
        List<Category> categories = cateRepo.findAll();
        model.addAttribute("categories", categories);
        return "admin/category-management";
    }
    @GetMapping("/category-management/add-category")
    public String addNewCatePage() {
        return "admin/category-form";
    }
    @PostMapping("/category-management/add-category")
    public String addNewCate(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             Model model) {
        if (name.isEmpty() || description.isEmpty()){
            model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            return "admin/category-form";
        }
        cateRepo.save(new Category(name, description, LocalDateTime.now(), LocalDateTime.now()));
        model.addAttribute("message", "Thêm mới thành công");
        return "admin/category-form";
    }
    @DeleteMapping("/category-management/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteCate(@PathVariable("id") Long id) {
        try {
            cateRepo.deleteById(id);
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Category deleted failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category-management/update/{id}")
    public String updateCatePage(@PathVariable("id") Long id, Model model) {
        Category category = cateRepo.findById(id).get();
        model.addAttribute("category", category);
        return "admin/category-form";
    }
    @PostMapping("/category-management/update/{id}")
    public String updateCate(@PathVariable("id") Long id,
                             @RequestParam("name") String name,
                             @RequestParam("description") String description,
                             Model model) {
        if (name.isEmpty() || description.isEmpty()){
            model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            return "admin/category-form";
        }
        Category category = cateRepo.findById(id).get();
        category.setName(name);
        category.setDescription(description);
        category.setUpdate_at(LocalDateTime.now());
        cateRepo.save(category);
        model.addAttribute("message", "Cập nhật thành công");
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @PostMapping("/product")
    public String addNewProduct() {
        return "product-form";
    }

    @PostMapping("/user/update-status")
    public String updateStatus(@RequestParam("id") Long id, @RequestParam("status") boolean status) {
        User user = userService.getUserById(id);
        user.setActive(!status);
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("order-management")
    public String orderManagement(Model model){
        model.addAttribute("orders", orderService.getAllOrder());
        return "admin/order-management";
    }
    @PostMapping("order-management/update-status/{id}")
    public String updateStatusOrder(@PathVariable("id") Long id, @RequestParam("orderStatus") String orderStatus) {
        Order order = orderService.getOrderById(id);
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));
        orderService.updateOrder(order);
        return "redirect:/admin/order-management";
    }
    @PostMapping("order-management/search-by-user-name")
    public String processSearch(@RequestParam String searchKeyWord, Model model) {
        List<Order> orders = orderService.searchOrderByUser(searchKeyWord);
        model.addAttribute("orders", orders);
        return "admin/order-management";
    }
    @GetMapping("order-management/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, Model model) {
        orderService.deleteOrder(id);
        return "redirect:/admin/order-management";
    }
    @PostMapping("product-management/search-by-product-name")
    public String processSearchProduct(@RequestParam String productName, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        List<Product> products = productService.searchProductByName(productName);
        model.addAttribute("products", products);
        return "admin/product-management";
    }
}
