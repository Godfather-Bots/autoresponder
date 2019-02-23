package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.Person;

import java.util.HashMap;
import java.util.Map;

public class PersonRepositoryMap implements PersonRepository {

    private Map<Integer, Person> people = new HashMap<>();

    @Override
    public void addPerson(Person person) {
        people.put(person.getId(), person);
    }

    @Override
    public void removePerson(Person person) {
        people.remove(person.getId());
    }

    @Override
    public void addPersonAll(Map<Integer, Person> personCollection) {
        people.putAll(personCollection);
    }

    @Override
    public Person getPersonById(Integer idPerson) {
        return people.get(idPerson);
    }
}