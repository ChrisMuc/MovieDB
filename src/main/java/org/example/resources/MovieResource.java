package org.example.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.example.core.Movie;
import org.example.db.MovieDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/movie/{movieId}")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final MovieDAO movieDAO;

    public MovieResource(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @GET
    @Timed   // used to record the duration and rate of invocations as a Metrics Timer.
    @UnitOfWork
    public Movie findMovie(@PathParam("movieId") LongParam id) {
        return movieDAO.findById(id.get());
    }

    @DELETE
    @UnitOfWork
    public Boolean deleteMovie(@PathParam("movieId") LongParam id) {
        return movieDAO.deleteById(id.get());
    }
}