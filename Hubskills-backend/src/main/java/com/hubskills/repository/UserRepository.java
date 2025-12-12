package com.hubskills.repository;

import com.hubskills.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode custom pour trouver un user par email
    Optional<User> findByEmail(String email);
}
