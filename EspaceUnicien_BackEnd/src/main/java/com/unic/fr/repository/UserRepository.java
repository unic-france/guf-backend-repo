package com.unic.fr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unic.fr.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
   
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);

	Optional<User> findByUuid(String uuid);
}