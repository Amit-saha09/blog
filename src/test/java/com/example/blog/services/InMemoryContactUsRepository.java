package com.example.blog.services;

import com.example.blog.model.ContactUs;
import com.example.blog.repositories.ContactUsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class InMemoryContactUsRepository implements ContactUsRepository {

    private final List<ContactUs> db = new ArrayList<>();
    private long idCounter = 1L;

    @Override
    public <S extends ContactUs> S save(S entity) {
        entity.setId(idCounter++);
        db.add(entity);
        return entity;
    }

    // Throw for unimplemented methods (to be safe)
    @Override public void deleteById(Long aLong) { throw new UnsupportedOperationException(); }
    @Override public boolean existsById(Long aLong) { throw new UnsupportedOperationException(); }
    @Override public List<ContactUs> findAll() { throw new UnsupportedOperationException(); }
    @Override public long count() { throw new UnsupportedOperationException(); }
    @Override public java.util.Optional<ContactUs> findById(Long aLong) { throw new UnsupportedOperationException(); }
    @Override public <S extends ContactUs> List<S> saveAll(Iterable<S> entities) { throw new UnsupportedOperationException(); }
    @Override public void delete(ContactUs entity) { throw new UnsupportedOperationException(); }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override public void deleteAll(Iterable<? extends ContactUs> entities) { throw new UnsupportedOperationException(); }
    @Override public void deleteAll() { throw new UnsupportedOperationException(); }
    @Override public List<ContactUs> findAllById(Iterable<Long> longs) { throw new UnsupportedOperationException(); }
    @Override public void flush() { throw new UnsupportedOperationException(); }
    @Override public <S extends ContactUs> S saveAndFlush(S entity) { throw new UnsupportedOperationException(); }

    @Override
    public <S extends ContactUs> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override public void deleteInBatch(Iterable<ContactUs> entities) { throw new UnsupportedOperationException(); }

    @Override
    public void deleteAllInBatch(Iterable<ContactUs> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override public void deleteAllInBatch() { throw new UnsupportedOperationException(); }
    @Override public ContactUs getOne(Long aLong) { throw new UnsupportedOperationException(); }
    @Override public ContactUs getById(Long aLong) { throw new UnsupportedOperationException(); }
    @Override public ContactUs getReferenceById(Long aLong) { throw new UnsupportedOperationException(); }

    @Override
    public <S extends ContactUs> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ContactUs> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ContactUs> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ContactUs> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ContactUs> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ContactUs> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ContactUs, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<ContactUs> findByIdAndIsDeleted(Long id, boolean isDeleted) {
        return Optional.empty();
    }

    @Override
    public List<ContactUs> findAllByIsDeletedOrderByIdDesc(boolean isDeleted) {
        return List.of();
    }

    @Override
    public Page<ContactUs> findAllByIsDeleted(boolean isDeleted, Pageable pageable) {
        return null;
    }

    @Override
    public List<ContactUs> findAllByIsDeletedFalse() {
        return List.of();
    }

    @Override
    public List<ContactUs> findAllByIdInAndIsDeleted(Set<Long> ids, boolean isDeleted) {
        return List.of();
    }

    @Override
    public List<ContactUs> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ContactUs> findAll(Pageable pageable) {
        return null;
    }
}

// Custom version of ContactUsService to call the private constructor directly
class TestableContactUsService extends ContactUsService {
    public TestableContactUsService(ContactUsRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }
}

