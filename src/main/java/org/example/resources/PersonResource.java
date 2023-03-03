package org.example.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.Person;
import org.example.db.PersonDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/person/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final PersonDAO personDAO;

    public PersonResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @Timed   // used to record the duration and rate of invocations as a Metrics Timer.
    @UnitOfWork
    public Person findPerson(@PathParam("personId") Optional<Long> id) {
        return personDAO.findById(
                id.orElseThrow(() -> new BadRequestException("person ID is required"))
        ).orElseThrow(() -> new NotFoundException("No such person."));
    }

    @PUT
    @UnitOfWork
    public Person updatePerson(@PathParam("personId") Optional<Long> id, Person person) {
        return personDAO.update(
                id.orElseThrow(() -> new BadRequestException("person ID is required")),
                person
        ).orElseThrow(() -> new NotFoundException("No such person."));
    }

    @DELETE
    @UnitOfWork
    public Response deletePerson(@PathParam("personId") Optional<Long> id) {
        return Response.status(personDAO.deleteById(
                id.orElseThrow(() -> new BadRequestException("person ID is required"))
        )? Response.Status.OK : Response.Status.NOT_FOUND).build();
    }
}