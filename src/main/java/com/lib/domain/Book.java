package com.lib.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book name cannot be null")
    @Size(min = 2,max = 80,message = "Book name '${validateValue}' should be between {min} and {max}")
    private String name;

    @NotNull(message = "Book name cannot be null")
    @Column(length = 17)
    private String isbn;

    @Column(nullable = false)
    private Integer pageCount;
    @NotNull(message = "Author cannot be null")
    private Long authorId;
    @NotNull(message = "Publisher cannot be null")
    private Long publisherId;


    private Integer publishDate;
    @NotNull(message = "CategoryId cannot be null")
    private Long categoryId;

    @OneToOne
    @Column(nullable = false)
    private ImageFile imageFile;

    private boolean loanable=true;

    @NotNull(message = "ShelfCode cannot be null")
    @Column(length = 6)
    private String shelfCode;


    private boolean active=true;
    private boolean featured=false;

    @NotNull
    private LocalDateTime createDate;


    private boolean builtIn =false;








}
