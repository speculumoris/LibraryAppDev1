package com.lib.dto.request;

import com.lib.domain.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateByUserRequest {



    @NotNull(message = "first name cannot be null")
    @Size(min = 2,max = 30,message = "First name '${validateValue}' should be between {min} and {max}")
    private String firstName;
    @NotNull(message = "last name cannot be null")
    @Size(min = 2,max = 30,message = "last name '${validateValue}' should be between {min} and {max}")
    private String lastName;

    @NotNull(message = "last name cannot be null")
    @Size(min = 10,max = 100,message = "address '${validateValue}' should be between {min} and {max}")
    private String address;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
            message = "Please provide valid phone number")
    @NotNull(message = "phone number cannot be null")
    private String phone;

    @Pattern(regexp = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate birthDate;

    @Email(message = "please provide valid email")
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;

    private Set<String> roles ;









}
