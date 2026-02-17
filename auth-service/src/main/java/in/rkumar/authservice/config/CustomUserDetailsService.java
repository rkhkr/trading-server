package in.rkumar.authservice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.rkumar.authservice.models.UserCredential;
import in.rkumar.authservice.repositories.UserCredentialRepository;

public class CustomUserDetailsService implements UserDetailsService {

	private final UserCredentialRepository repository;

    public CustomUserDetailsService(UserCredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByUserId(userId);
        return credential.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }
}