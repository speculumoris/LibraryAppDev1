package com.lib.repository;

import com.lib.domain.Publisher;
import com.lib.dto.PublisherDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository <Publisher,Long> {


    @Query("SELECT count(*) from Publisher p join p.image img where img.id=:id")
    Integer findPublisherCountByImageId(@Param("id") String id);

    @EntityGraph(attributePaths = {"image"})
    Optional<Object> findPublisherById(Long id);

    @Query("Select p from Publisher p join p.image im where im.id=:id")
    List<Publisher> findPublisherByImageId(@Param("id") String id);
}
