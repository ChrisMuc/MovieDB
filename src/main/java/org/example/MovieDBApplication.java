package org.example;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.example.core.Movie;
import org.example.db.MovieDAO;
import org.example.resources.MovieResource;
import org.example.resources.MoviesResource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieDBApplication extends Application<MovieDBConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MovieDBApplication().run(args);
    }


    private final HibernateBundle<MovieDBConfiguration> hibernate = new HibernateBundle<MovieDBConfiguration>(Movie.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(MovieDBConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "MovieDB";
    }

    @Override
    public void initialize(final Bootstrap<MovieDBConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final MovieDBConfiguration configuration, final Environment environment) {
        final MovieDAO movieDAO = new MovieDAO(hibernate.getSessionFactory());

        environment.jersey().register(new MovieResource(movieDAO));
        environment.jersey().register(new MoviesResource(movieDAO));

        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("MovieDB API")
                .version("1.0.0")
                .description("RESTful greetings for you.")
                .termsOfService("http://example.org/terms")
                .contact(new Contact().email("christian.hass.muc@gmail.com"));

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of("org.example")
                        .collect(Collectors.toSet()));
        environment.jersey().register(new OpenApiResource()
                .openApiConfiguration(oasConfig));
    }

}
