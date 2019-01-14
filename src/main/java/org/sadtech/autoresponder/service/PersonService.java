package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Person;

public interface PersonService {

    Person getPersonById(Integer integer);
    void addPerson(Person person);
    boolean checkPerson(Integer idPerson);

}
