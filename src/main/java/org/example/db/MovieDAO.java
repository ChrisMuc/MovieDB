package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.core.Movie;
import org.hibernate.SessionFactory;

import java.util.List;

public class MovieDAO extends AbstractDAO<Movie> {
    public MovieDAO(SessionFactory factory) {
        super(factory);
    }

    public Movie findById(Long id) {
        return get(id);
    }

    public long create(Movie Movie) {
        return persist(Movie).getId();
    }

    public List<Movie> findAll() {
        return list(namedTypedQuery("org.example.core.Movie.findAll"));
    }
}