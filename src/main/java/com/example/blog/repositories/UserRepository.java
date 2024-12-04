package com.example.blog.repositories;

import com.example.blog.model.Faq;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is to provide faq repository
 * @author Amit Saha
 * @since 25th september
 */
@Repository
public interface UserRepository extends ServiceRepository<User> {

   User findByEmailAndIsActivatedTrue(String email);
   Optional<User> findByEmail(String username);
   Long countByEmailAndIsDeletedFalse(String email);

   @Query(value = "select u from User u " +
           "where u.isDeleted = false " +
           "and (:email is null or u.email like %:email%) " +
           "and (:phone is null or u.phone = :phone) ")
   List<User> findBySearch(@Param("email") String email, @Param("phone") String phone);

   /******author-->Simi********/
   // New method to find all activated users
   List<User> findByIsActivatedTrue();
}
