package com.example.myAirlineUserService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myAirlineUserService.models.ConfirmationToken;


/**
 * Repository interface for {@link ConfirmationToken}.
 * 
 * @since 0.0.1
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    
    Optional<ConfirmationToken> findByUserEmail(String email);

    Optional<ConfirmationToken> findByToken(String token);

    Boolean existsByToken(String token);
}