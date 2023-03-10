package com.lib.repository;

import com.lib.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query("SELECT count(*) from Category c join c.book bk where bk.id=:id")
    Integer findCategoryCountByBookId(Long id);

    @EntityGraph(attributePaths = {"book"})
    List<Category> findAll();

    @EntityGraph(attributePaths = {"book"})
    Page<Category> findAll(Pageable pageable);

    @Query("Select c from Category c join c.book bk where bk.id=:id")
    List<Category> findCategoriesByBookId(Long bookId);


}
