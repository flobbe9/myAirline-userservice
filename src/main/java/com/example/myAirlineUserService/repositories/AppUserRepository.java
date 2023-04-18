package com.example.myAirlineUserService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myAirlineUserService.models.AppUser;


/**
 * Repository interface for {@link AppUser}.
 * 
 * @since 0.0.2
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    Optional<AppUser> findByEmail(String email);
}