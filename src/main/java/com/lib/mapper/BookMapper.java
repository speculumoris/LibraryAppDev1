package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.domain.ImageFile;
import com.lib.dto.BookDTO;
import com.lib.dto.ImageFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {



    List<BookDTO> map(List<Book> books);

    @Mapping(target = "image",ignore = true)
    Book bookDTOToBook(BookDTO bookDTO);


//    @Mapping(target = "imageFile",ignore = true)
    @Mapping(source = "image",target = "image",qualifiedByName = "getImageAsString")
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
