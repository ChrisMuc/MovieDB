package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.core.Person;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PersonDAO extends AbstractDAO<Person> {
    public PersonDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public boolean deleteById(Long id) {
        Object object = get(id);
        if (object != null) {
            //noinspection resource
            currentSession().delete(object); // Do not use auto-close, otherwise delete will be reverted (and other side effects could arise)!
            return true;
        }
        return false;
    }

    public long create(Person person) {
        return persist(person).getId();
    }

    public Optional<Person> update(Long id, Person person) {
        Person current = get(id);
        if (current == null) {
            return Optional.empty();
        } else {
            currentSession().evict(current);
        }
        person.setId(id);
        return Optional.of((Person) currentSession().merge(person));
    }

    public List<Person> findAll() {
        return list(namedTypedQuery("org.example.core.Person.findAll"));
    }

    public List<Person> findByName(String name) {
        return list(namedTypedQuery("org.example.core.Person.findByName")
                .setParameter("name", "%" + name + "%")
        );
    }
}