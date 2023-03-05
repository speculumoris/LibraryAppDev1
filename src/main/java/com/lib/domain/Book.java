package com.lib.domain;

import jakarta.persistence.*;
<<<<<<<<< Temporary merge branch 1
import jakarta.validation.constraints.Pattern;
=========
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
>>>>>>>>> Temporary merge branch 2
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

<<<<<<<<< Temporary merge branch 1
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d{1}$")
=========
    @NotNull(message = "Book name cannot be null")
    @Column(length = 17)
>>>>>>>>> Temporary merge branch 2
    private String isbn;

    @Column(nullable = false)
    private Integer pageCount;
    @NotNull(message = "Author cannot be null")
    private Long authorId;
    @NotNull(message = "Publisher cannot be null")
    private Long publisherId;

<<<<<<<<< Temporary merge branch 1
    @Pattern(regexp = "^\\d{4}$", message = "please enter : yyyy" )
    @Column(nullable = false)
=========

>>>>>>>>> Temporary merge branch 2
    private Integer publishDate;
    @NotNull(message = "CategoryId cannot be null")
    private Long categoryId;

<<<<<<<<< Temporary merge branch 1
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<ImageFile> imageFile;

    private boolean loanable;

    @Pattern(regexp = "^\\s{2}-\\d{3}$")
=========
    @OneToOne
    @Column(nullable = false)
    private ImageFile imageFile;

    private boolean loanable=true;

    @NotNull(message = "ShelfCode cannot be null")
    @Column(length = 6)
>>>>>>>>> Temporary merge branch 2
    private String shelfCode;


    private boolean active=true;
    private boolean featured=false;

    @NotNull
    private LocalDateTime createDate;


    private boolean builtIn =false;




}
