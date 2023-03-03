package org.example.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.Person;
import org.example.db.PersonDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {
    private final PersonDAO personDAO;

    public PeopleResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @POST
    @UnitOfWork
    public long createPerson(@Valid Person person) {
        return personDAO.create(person);
    }

    @GET
    @UnitOfWork
    public List<Person> listPeople(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return personDAO.findByName(name.get());
        } else {
            return personDAO.findAll();
        }
    }

}