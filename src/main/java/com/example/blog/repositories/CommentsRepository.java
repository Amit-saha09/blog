package com.example.blog.repositories;

import com.example.blog.model.AboutUs;
import com.example.blog.model.Comments;
import org.springframework.stereotype.Repository;

/**
 * This interface is to provide faq repository
 * @author Amit Saha
 * @since 25th september
 */
@Repository
public interface CommentsRepository extends ServiceRepository<Comments> {


}
