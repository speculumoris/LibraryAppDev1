package com.lib.dto;


import com.lib.domain.ImageFile;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {


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

}
