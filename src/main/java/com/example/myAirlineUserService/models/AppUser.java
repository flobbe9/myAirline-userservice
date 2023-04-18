package com.example.myAirlineUserService.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.example.myAirlineUserService.models.validation.ValidAppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Class defining the User entity.
 * 
 * @since 0.0.2
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ValidAppUser(groups = {ValidAppUser.class})
public class AppUser {
    
    @Id
    @GenericGenerator(name = "user_id_generator", 
                    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                    parameters = {
                        @Parameter(name ="initial_value", value = "2")
                    })
    @GeneratedValue(generator = "flight_id_generator")    
    private Long id;

    @NotBlank(message = "First name cannot be blank.", groups = {ValidAppUser.class})
    private String firstName;

    @NotBlank(message = "Surname cannot be blank.", groups = {ValidAppUser.class})
    private String surName;

    @NotBlank(message = "Email cannot be blank.", groups = {ValidAppUser.class})
    @EqualsAndHashCode.Include
    private String email;

    @NotBlank(message = "Password cannot be blank.", groups = {ValidAppUser.class})
    private String password;

    @NotNull(message = "User role cannot be null.", groups = {ValidAppUser.class})
    private AppUserRole role;


    public AppUser(@NotBlank(message = "First name cannot be blank.", groups = ValidAppUser.class) String firstName,
                    @NotBlank(message = "Surname cannot be blank.", groups = ValidAppUser.class) String surName,
                    @NotBlank(message = "Email cannot be blank.", groups = ValidAppUser.class) String email,
                    @NotBlank(message = "Password cannot be blank.", groups = ValidAppUser.class) String password,
                    @NotNull(message = "User role cannot be null.", groups = ValidAppUser.class) AppUserRole role) {

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
}