package org.foxminded.university.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractDao<K extends Number, T> {
    protected JdbcTemplate jdbcTemplate;

    abstract void create(T entity);

    abstract Optional<T> findById(K id);

    abstract void update(T entity);

    abstract void delete(K id);

    abstract List<T> findAll();

    abstract Pageable<T> findAll(Page page);
}
