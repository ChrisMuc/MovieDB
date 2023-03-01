package org.example.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.Movie;
import org.example.db.MovieDAO;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public List<Movie> listMovies() {
        return movieDAO.findAll();
    }

}