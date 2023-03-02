package org.example.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.Movie;
import org.example.db.MovieDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
    private final MovieDAO movieDAO;

    public MoviesResource(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @POST
    @UnitOfWork
    public long createMovie(@Valid Movie movie) {
        return movieDAO.create(movie);
    }

    @GET
    @UnitOfWork
    public List<Movie> listMovies(@QueryParam("publicationYear") Optional<Integer> publicationYear) {
        if (publicationYear.isPresent()) {
            return movieDAO.findByPublicationYear(publicationYear.get());
        } else {
            return movieDAO.findAll();
        }
    }

}