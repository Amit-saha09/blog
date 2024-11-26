package com.example.blog.repositories;

import com.example.blog.model.Category;
import org.springframework.stereotype.Repository;

/**
 * This interface is to provide faq repository
 * @author Amit Saha
 * @since 25th september
 */
@Repository
public interface CategoryRepository extends ServiceRepository<Category> {

    Long countAllByNameAndIsDeletedFalse(String name);

}
