package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.core.Movie;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MovieDAO extends AbstractDAO<Movie> {
    public MovieDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public boolean deleteById(Long id) {
        Object object = get(id);
        if (object != null) {
//            try (Session session = currentSession()) {
//                session.delete(object);
//                session.flush(); // if session is not flushed, auto-close will revert
//            }
            //noinspection resource
            currentSession().delete(object); // Do not use auto-close, otherwise delete will be reverted (and other side effects could arise)!
            return true;
        }
        return false;
    }

    public long create(Movie Movie) {
        return persist(Movie).getId();
    }

    public Optional<Movie> update(Long id, Movie movie) {
        Movie current = get(id);
        if (current == null) {
            return Optional.empty();
        } else {
            currentSession().evict(current);
        }
        movie.setId(id);
        return Optional.of((Movie) currentSession().merge(movie));
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