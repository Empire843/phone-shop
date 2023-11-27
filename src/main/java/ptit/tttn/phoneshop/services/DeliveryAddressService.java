package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.DeliveryAddress;
import ptit.tttn.phoneshop.repositories.DeliveryAddressRepository;

import java.util.List;

@Service
public class DeliveryAddressService {
    @Autowired
    private DeliveryAddressRepository repo;

    public DeliveryAddress save(DeliveryAddress address) {
        return  repo.save(address);
    }

    public List<DeliveryAddress> getAllAddress() {
        return repo.findAll();
    }

    public DeliveryAddress getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Address not found!"));
    }

    public void deleteById(Long id) {
        if (repo.existsById(id))
            repo.deleteById(id);
        else {
            throw new RuntimeException("Address not found!");
        }
    }
}
