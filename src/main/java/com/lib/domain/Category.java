package com.lib.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "t_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category cannot be null")
    @Size(min = 4,max = 80,message = "Author name '${validateValue}' should be between {min} and {max}")
    private String name;
    private Boolean builtIn=false;

    @NotNull(message = "Author name cannot be null")
    @Size(min = 4,max = 70,message = "Author name '${validateValue}' should be between {min} and {max}")
    private int sequence;

    @OneToMany(mappedBy = "category")
    private List<Book> bookList;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="category_id")
    private Set<Book> book;


    public Category(String name, boolean builtIn, int sequence) {
        this.id = id;
        this.name = name;
        this.builtIn = builtIn;
        this.sequence = this.getSequence()+1;
    }

}
