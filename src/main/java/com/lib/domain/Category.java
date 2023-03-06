package com.lib.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    private boolean builtIn=false;

    @NotNull(message = "Author name cannot be null")
    @Size(min = 4,max = 70,message = "Author name '${validateValue}' should be between {min} and {max}")
    private int sequence;

    @JsonIgnore
    @OneToMany(mappedBy = "category")

    private List<Book> bookList;


    public Category(String name, boolean builtIn, int sequence) {
        this.id = id;
        this.name = name;
        this.builtIn = builtIn;
        this.sequence = this.getSequence()+1;
    }
}
