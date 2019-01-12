package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.Person;

import java.util.Collection;
import java.util.Map;

public interface PersonRepository {

    void addPerson(Person person);
    void removePerson(Person person);
    void addPersonAll(Map<Integer, Person> personCollection);
    Person getPersonById(Integer idPerson);

}
