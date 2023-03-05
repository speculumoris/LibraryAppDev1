package com.lib.dto.request;

import com.lib.domain.ImageFile;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor
public class BookRequest {

    @NotNull(message = "Book name cannot be null")
    @Size(min = 2,max = 80,message = "Book name '${validateValue}' should be between {min} and {max}")
    private String name;

    @NotNull(message = "Book name cannot be null")
    private String isbn;

    private Integer pageCount;
    @NotNull(message = "Author cannot be null")
    private Long authorId;
    @NotNull(message = "Publisher cannot be null")
    private Long publisherId;


    private Integer publishDate;
    @NotNull(message = "CategoryId cannot be null")
    private Long categoryId;

    private String imageFileId;

    private boolean loanable=true;

    @NotNull(message = "ShelfCode cannot be null")

    private String shelfCode;


    private boolean active=true;
    private boolean featured=false;
}