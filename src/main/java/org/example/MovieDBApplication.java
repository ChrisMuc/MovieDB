package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MovieDBApplication extends Application<MovieDBConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MovieDBApplication().run(args);
    }

    @Override
    public String getName() {
        return "MovieDB";
    }

    @Override
    public void initialize(final Bootstrap<MovieDBConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final MovieDBConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
