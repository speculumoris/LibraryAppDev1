package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.domain.ImageFile;
import com.lib.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {


    @Mapping(target = "image",ignore = true)
    List<BookDTO> map(List<Book> bookList);

    @Mappings({

            @Mapping(source="categoryId", target = "category"),
            @Mapping(source="publisherId", target = "publisher"),
            @Mapping(source="authorId", target="author"),
            @Mapping(source = "imageFile",target = "imageFile")
    })
    Book bookDTOToBook(BookDTO bookDTO);
    @Mappings({

            @Mapping(source="category", target = "categoryId"),
            @Mapping(source="publisher", target = "publisherId"),
            @Mapping(source="author", target="authorId"),
            @Mapping(source = "imageFile",target = "imageFile",qualifiedByName = "getImageAsString")
    })
    BookDTO bookToBookDTO(Book book);

    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles){

        Set<String> imgs = new HashSet<>();

        imgs = imageFiles.stream().
                map(imFile-> imFile.getId().toString()).
                collect(Collectors.toSet());

        return imgs;
    }




}
