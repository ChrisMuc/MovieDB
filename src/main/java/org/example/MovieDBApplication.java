package org.example;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.core.Movie;
import org.example.db.MovieDAO;
import org.example.resources.MoviesResource;

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

        environment.jersey().register(new MoviesResource(movieDAO));
    }

}
