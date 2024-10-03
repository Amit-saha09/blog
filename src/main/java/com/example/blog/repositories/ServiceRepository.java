package com.example.blog.repositories;

import com.example.blog.model.AuditModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This interface is to provide ServiceRepository
 *
 * @author Amit Saha
 * @since 25 september 2025
 */
@NoRepositoryBean
public interface ServiceRepository<E extends AuditModel> extends JpaRepository<E, Long> {

    Optional<E> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<E> findAllByIsDeletedOrderByIdDesc(boolean isDeleted);

    Page<E> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    List<E> findAllByIsDeletedFalse();

    List<E> findAllByIdInAndIsDeleted(Set<Long> ids, boolean isDeleted);
}
