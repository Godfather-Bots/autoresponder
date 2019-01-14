package org.sadtech.autoresponder.service.impl;

import lombok.AllArgsConstructor;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.repository.PersonRepository;
import org.sadtech.autoresponder.service.PersonService;

@AllArgsConstructor
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

    @Override
    public boolean checkPerson(Integer idPerson) {
        return personRepository.getPersonById(idPerson) != null;
    }
}
