package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.dto.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookDTO> map(List<Book> bookList);
}
