package com.example.myAirlineUserService.models;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Class defining a token to confirm an {@link AppUser}s email.
 * 
 * @since 0.0.2
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfirmationToken {
    
    @Id
    @GeneratedValue    
    private Long id;

    @NotBlank
    @EqualsAndHashCode.Include
    private String token;

    @NotBlank(message = "User email cannot be blank.")
    private String userEmail;

    @NotNull(message = "CreatedAt cannot be null.")
    @DateTimeFormat
    private LocalDateTime createdAt;

    @NotNull(message = "ExpiresAt cannot be null.")
    @DateTimeFormat
    private LocalDateTime expiresAt;

    @DateTimeFormat
    private LocalDateTime confirmedAt;

    
    public ConfirmationToken(@NotBlank String token,
                            @NotBlank(message = "User email cannot be blank.") String userEmail,
                            @NotNull(message = "CreatedAt cannot be null.") LocalDateTime createdAt,
                            @NotNull(message = "ExpiresAt cannot be null.") LocalDateTime expiresAt,
                            @NotNull(message = "ConfirmedAt cannot be null.") LocalDateTime confirmedAt) {
        
        this.token = token;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
    }


    /**
     * Checks if the expiring time has been reached yet.
     * 
     * @return true if expiresAt is in the past or now
     */
    public boolean isExpired() {

        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(this.expiresAt) || now.equals(this.expiresAt);
    }
}