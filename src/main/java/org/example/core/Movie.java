package org.example.core;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "movies")
@NamedQuery(
        query = "SELECT m FROM Movie m",
        name = "org.example.core.Movie.findAll"
)
@NamedQuery(
        query = "SELECT m FROM Movie m WHERE m.publicationYear=:publicationYear",
        name = "org.example.core.Movie.findByPublicationYear"
)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "description", nullable = false, length = 32768)
    @NotEmpty
    private String description;

    @Column(name = "publicationYear", nullable = true)
    @Min(value = 1800)
    @Max(value = 9999)
    private Integer publicationYear;

    public Movie() {
    }

    public Movie(String name, String description, Integer publicationYear) {
        this.name = name;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Movie)) {
            return false;
        }

        Movie other = (Movie) o;

        return id == other.id &&
                Objects.equals(name, other.name) &&
                Objects.equals(description, other.description) &&
                Objects.equals(publicationYear, other.publicationYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, publicationYear);
    }
}