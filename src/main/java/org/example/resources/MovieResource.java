package org.example.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.Movie;
import org.example.db.MovieDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
    public Movie findMovie(@PathParam("movieId") Optional<Long> id) {
        return movieDAO.findById(
                id.orElseThrow(() -> new BadRequestException("movie ID is required"))
        ).orElseThrow(() -> new NotFoundException("No such movie."));
    }

    @PUT
    @UnitOfWork
    public Movie update(@PathParam("movieId") Optional<Long> id, Movie movie) {
        return movieDAO.update(
                id.orElseThrow(() -> new BadRequestException("movie ID is required")),
                movie
        ).orElseThrow(() -> new NotFoundException("No such movie."));
    }

    @DELETE
    @UnitOfWork
    public Response deleteMovie(@PathParam("movieId") Optional<Long> id) {
        return Response.status(movieDAO.deleteById(
                id.orElseThrow(() -> new BadRequestException("movie ID is required"))
        )? Response.Status.OK : Response.Status.NOT_FOUND).build();
    }
}