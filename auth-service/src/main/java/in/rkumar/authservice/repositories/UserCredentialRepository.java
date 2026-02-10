package in.rkumar.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rkumar.authservice.models.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    
    // Custom method to find a user by their name
    Optional<UserCredential> findByName(String username);
}