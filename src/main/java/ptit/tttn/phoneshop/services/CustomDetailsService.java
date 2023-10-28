package ptit.tttn.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ptit.tttn.phoneshop.models.CustomUserDetails;
import ptit.tttn.phoneshop.models.User;
import ptit.tttn.phoneshop.repositories.UserRepository;

@Service
public class CustomDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails((User) repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
