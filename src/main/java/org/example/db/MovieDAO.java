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

    public boolean deleteById(Long id) {
        Object object = get(id);
        if (object != null) {
            currentSession().delete(object);
            return true;
        }
        return false;
    }

    public long create(Movie Movie) {
        return persist(Movie).getId();
    }

    public Movie update(Long id, Movie movie) {
        movie.setId(id);
        return persist(movie);
    }

    public List<Movie> findAll() {
        return list(namedTypedQuery("org.example.core.Movie.findAll"));
    }

    public List<Movie> findByPublicationYear(Integer publicationYear) {
        return list(namedTypedQuery("org.example.core.Movie.findByPublicationYear")
                .setParameter("publicationYear", publicationYear)
        );
    }
}