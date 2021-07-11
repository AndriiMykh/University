package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<K extends Number, T> {

    void create(T entity);

    Optional<T> findById(K id);

    void update(T entity);

    void delete(K id);

    List<T> findAll();

    Pageable<T> findAll(Page page);

}
