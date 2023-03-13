package com.lib.dto;


import com.lib.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Boolean builtIn=false;
    private int sequence;

    private List<Book> bookList;

    private Set<Book> book;
}
