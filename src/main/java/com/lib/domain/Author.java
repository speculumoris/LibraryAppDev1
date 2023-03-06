package com.lib.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Author name cannot be null")
    @Size(min = 4,max = 70,message = "Author name '${validateValue}' should be between {min} and {max}")
    private String name;


    private boolean builtIn =false;

    @OneToMany(mappedBy = "author")
    private List<Book> bookList;


}
