package com.example.blog.repositories;

import com.example.blog.model.AboutUs;
import com.example.blog.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is to provide faq repository
 * @author Amit Saha
 * @since 25th september
 */
@Repository
public interface PostRepository extends ServiceRepository<Post> {

    @Query(value = "select p from Post p " +
            "where p.isDeleted = false " +
            "and (:description is null or p.description like %:description%) " +
            "and (:categoryId is null or p.category.id = :categoryId) " +
            "and (:userEmail is null or p.user.email = :userEmail)")
    List<Post> findBySearch(@Param("description") String description, @Param("categoryId") Long categoryId
    ,@Param("userEmail") String userEmail);

}
