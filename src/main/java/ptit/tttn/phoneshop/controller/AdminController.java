package ptit.tttn.phoneshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.tttn.phoneshop.models.Category;
import ptit.tttn.phoneshop.models.Product;
import ptit.tttn.phoneshop.repositories.CategoryRepository;
import ptit.tttn.phoneshop.request.body.ProductBody;
import ptit.tttn.phoneshop.services.ProductService;

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
    private static final String ADMIN_DASHBOARD = "admin/dashboard";
    @GetMapping(value = {"/","", "/dashboard"})
    public String dashboard() {
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

        Product prod = productService.createNewProduct(product, files);
        redirectAttributes.addFlashAttribute("message", "Thêm mới thành công");
        model.addAttribute("product", prod);
        List<Category> categories = cateRepo.findAll();
        model.addAttribute("categories", categories);
        return "admin/product-form";
    }

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
    @GetMapping("/category-management/delete/{id}")
    public String deleteCate(@PathVariable("id") Long id) {
        cateRepo.deleteById(id);
        return "redirect:/admin/category-management";
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


}
