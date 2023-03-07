package com.lib.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_publisher")
public class Publisher {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be white spase")
    @Size(min = 2, max = 25, message = "Name '${validatedValue}' must be between {min} and {max} long")
    private String name;

    private Boolean builtIn = false;

    @JsonIgnore
    @OneToMany
    private List<Book> bookList;





}
