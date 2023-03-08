package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {


    @Mapping(target = "image",ignore = true)
    List<BookDTO> map(List<Book> bookList);

    Book bookDTOToBook(BookDTO bookDTO);

    @Mapping(source = "image",target = "image",qualifiedByName = "getImageAsString")
    BookDTO bookToBookDTO(Book book);


}
