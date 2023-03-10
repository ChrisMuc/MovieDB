package org.example;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class MovieDBConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @NotNull
    private Boolean enableOpenApiSpec;

    @JsonProperty
    public Boolean getEnableOpenApiSpec() {
        return enableOpenApiSpec;
    }

    @JsonProperty
    public void setEnableOpenApiSpec(Boolean enableOpenApiSpec) {
        this.enableOpenApiSpec = enableOpenApiSpec;
    }
}
