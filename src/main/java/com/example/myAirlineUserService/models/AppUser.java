package com.example.myAirlineUserService.models;

import java.util.Collection;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.myAirlineUserService.models.validation.ValidAppUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Class defining the User entity.
 * 
 * @since 0.0.1
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ValidAppUser(groups = {ValidAppUser.class})
public class AppUser implements UserDetails {
    
    @Id
    @GenericGenerator(name = "user_id_generator", 
                    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                    parameters = {
                        @Parameter(name ="initial_value", value = "2")
                    })
    @GeneratedValue(generator = "user_id_generator")    
    private Long id;

    @NotBlank(message = "First name cannot be blank.", groups = {ValidAppUser.class})
    private String firstName;

    @NotBlank(message = "Surname cannot be blank.", groups = {ValidAppUser.class})
    private String surName;

    // TODO: add email regex
    @NotBlank(message = "Email cannot be blank.", groups = {ValidAppUser.class})
    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String email;

    // TODO: add password regex
    @NotBlank(message = "Password cannot be blank.", groups = {ValidAppUser.class})
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User role cannot be null", groups = {ValidAppUser.class})
    private AppUserRole role;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = false;


    public AppUser(@NotBlank(message = "First name cannot be blank.", groups = ValidAppUser.class) String firstName,
                    @NotBlank(message = "Surname cannot be blank.", groups = ValidAppUser.class) String surName,
                    @NotBlank(message = "Email cannot be blank.", groups = ValidAppUser.class) String email,
                    @NotBlank(message = "Password cannot be blank.", groups = ValidAppUser.class) String password,
                    AppUserRole role) {

        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    @Override
    public String toString() {

        return this.firstName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return role.getGrantedAuthorities();
    }


    @Override
    public String getUsername() {

        return email;
    }


    @Override
    public boolean isAccountNonExpired() {

        return isAccountNonExpired;
    }


    @Override
    public boolean isAccountNonLocked() {

        return isAccountNonLocked;
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return isCredentialsNonExpired;
    }


    @Override
    public boolean isEnabled() {

        return isEnabled;
    }
}