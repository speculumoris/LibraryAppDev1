package com.lib.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d{1}$")
    private String isbn;

    private Integer pageCount;

    private Long authorId;
    private Long publisherId;

    @Pattern(regexp = "^\\d{4}$", message = "please enter : yyyy" )
    @Column(nullable = false)
    private Integer publishDate;
    private Long categoryId;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<ImageFile> imageFile;

    private boolean loanable;

    @Pattern(regexp = "^\\s{2}-\\d{3}$")
    private String shelfCode;

    private boolean active;
    private boolean featured;

    private LocalDateTime createDate;
    private boolean builtIn;




}
