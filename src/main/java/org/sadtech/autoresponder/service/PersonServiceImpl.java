package org.sadtech.autoresponder.service;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.repository.PersonRepository;
import org.sadtech.autoresponder.repository.PersonRepositoryMap;

public class PersonServiceImpl implements PersonService {

    private static final Logger log = Logger.getLogger(PersonServiceImpl.class);

    private PersonRepository personRepository;

    public PersonServiceImpl() {
        this.personRepository = new PersonRepositoryMap();
    }

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getPersonById(Integer integer) {
        return personRepository.getPersonById(integer);
    }

    @Override
    public void addPerson(Person person) {
        personRepository.addPerson(person);
        log.info("Пользователь отправлен в репозиторий");
    }

    @Override
    public boolean checkPerson(Integer idPerson) {
        return personRepository.getPersonById(idPerson) != null;
    }
}
