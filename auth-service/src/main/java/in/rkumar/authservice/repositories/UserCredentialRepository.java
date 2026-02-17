package in.rkumar.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rkumar.authservice.models.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

	// For login (Token Generation)
	Optional<UserCredential> findByUserId(String userId);
	
	// For checking duplicate emails during registration
	Optional<UserCredential> findByEmail(String email);

}