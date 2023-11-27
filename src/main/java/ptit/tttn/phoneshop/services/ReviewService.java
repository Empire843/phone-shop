package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.Review;
import ptit.tttn.phoneshop.repositories.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repo;

    public Review create(Review review) {
        return repo.save(review);
    }

    public List<Review> getAllByProductId(Long productId) {
        return repo.getAllByProductId(productId);
    }
}
