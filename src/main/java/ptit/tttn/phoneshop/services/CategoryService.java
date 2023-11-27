package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.Category;
import ptit.tttn.phoneshop.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public List<Category> getAllCategory() {
        return repo.findAll();
    }
    public Category getById(Long categoryId) {
        return repo.findById(categoryId).get();
    }
}
