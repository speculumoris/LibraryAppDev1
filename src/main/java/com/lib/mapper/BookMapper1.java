package com.lib.mapper;

import com.lib.domain.*;
import com.lib.dto.BookDTO;
import com.lib.dto.request.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper1 {

    @Mapping(target = "image", ignore = true)
    List<BookDTO> map(List<Book> bookList);


    Book bookRequestToBook(BookRequest bookRequest);


    @Mapping(source = "category", target = "categoryId", qualifiedByName = "getCategoryId")
    @Mapping(source = "publisher", target = "publisherId", qualifiedByName = "getPublisherId")
    @Mapping(source = "author", target = "authorId", qualifiedByName = "getAuthorId")
    @Mapping(source = "imageFile", target = "imageFile", qualifiedByName = "getImageAsString")
    BookDTO bookToBookDTO(Book book);


    @Mapping(source = "categoryId", target = "category", qualifiedByName = "getCategory")
    @Mapping(source = "publisher", target = "publisher", qualifiedByName = "getPublisher")
    @Mapping(source = "authorId", target = "author", qualifiedByName = "getAuthor")
    @Mapping(source = "imageFile", target = "imageFile", qualifiedByName = "getImageAsString")
    Book bookDTOToBook(BookDTO bookDTO);


    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles) {

        Set<String> imgs = new HashSet<>();

        imgs = imageFiles.stream().
                map(imFile -> imFile.getId().toString()).
                collect(Collectors.toSet());

        return imgs;
    }

    @Named("getCategoryId")
    public static Long getCategoryId(Category category) {
        return category.getId();
    }

    @Named("getPublisherId")
    public static Long getPublisherId(Publisher publisher) {
        return publisher.getId();
    }

    @Named("getAuthorId")
    public static Long getAuthorId(Author author) {
        return author.getId();
    }

}
