package org.sadtech.autoresponder.service.impl;

import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.repository.PersonRepository;
import org.sadtech.autoresponder.service.PersonService;

public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;


    @Override
    public Person getPersonById(Integer integer) {
        return personRepository.getPersonById(integer);
    }

    @Override
    public void addPerson(Person person) {
        personRepository.addPerson(person);
    }
}
