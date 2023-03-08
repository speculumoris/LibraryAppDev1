package com.lib.repository;

import com.lib.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT count(*) from Book b join b.image img where img.id=:id")
    Integer findBookCountByImageId(String id);


}
