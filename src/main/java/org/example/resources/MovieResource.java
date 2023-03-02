package org.example.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.example.core.Movie;
import org.example.db.MovieDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @PUT
    @UnitOfWork
    public Movie update(@PathParam("movieId") LongParam id, Movie movie) {
        return movieDAO.update(id.get(), movie);
    }

    @DELETE
    @UnitOfWork
    public Response deleteMovie(@PathParam("movieId") LongParam id) {
        return Response.status(movieDAO.deleteById(id.get())? Response.Status.OK : Response.Status.NOT_FOUND).build();
    }
}