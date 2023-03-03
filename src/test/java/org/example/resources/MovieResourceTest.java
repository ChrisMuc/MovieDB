package org.example.resources;

import org.example.core.Movie;
import org.example.db.MovieDAO;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class MovieResourceTest {
    private static final MovieDAO DAO = mock(MovieDAO.class);
    public static final ResourceExtension RULE = ResourceExtension.builder()
            .addResource(new MovieResource(DAO))
            .build();

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getMovieSuccess() {
        final Movie movie = new Movie();
        movie.setId(1L);

        when(DAO.findById(1L)).thenReturn(Optional.of(movie));

        Movie found = RULE.target("/movie/1").request().get(Movie.class);

        assertThat(found.getId()).isEqualTo(movie.getId());
        verify(DAO).findById(1L);
    }

    @Test
    void getMovieNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.empty());
        final Response response = RULE.target("/movie/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }

}
