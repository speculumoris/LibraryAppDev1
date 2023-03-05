package com.lib.dto;

<<<<<<<<< Temporary merge branch 1

=========
import com.lib.domain.ImageFile;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
>>>>>>>>> Temporary merge branch 2
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<<<< Temporary merge branch 1
=========

>>>>>>>>> Temporary merge branch 2


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

<<<<<<<<< Temporary merge branch 1
=========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String isbn;

    private Integer pageCount;

    private Long authorId;
    private Long publisherId;
    private Integer publishDate;
    private Long categoryId;

    private ImageFile imageFile;
    private boolean loanable;

    private String shelfCode;

    private boolean active;
    private boolean featured;

    private LocalDateTime createDate;
    private boolean builtIn;
>>>>>>>>> Temporary merge branch 2
}
