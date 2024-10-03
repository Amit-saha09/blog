package com.example.blog.repositories;

import com.example.blog.model.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is to provide faq repository
 * @author Amit Saha
 * @since 25th september
 */
@Repository
public interface FaqRepository extends ServiceRepository<Faq> {

    Long countAllByIsDeletedFalseAndQuestionTitle(String questionTitle);

    Long countAllByIsDeletedFalseAndQuestionTitleAndIdNot(String questionTitle, Long id);

    Page<Faq> findByIsDeletedFalse(Pageable pageable);

    List<Faq> findByFaqStatusTrueAndIsDeletedFalse();
}
