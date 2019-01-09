package org.sadtech.consultant.processing;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Person;
import org.sadtech.consultant.database.service.PersonService;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class PersonLogicService {

    private PersonService personService;

    public PersonLogicService(PersonService userService) {
        this.personService = userService;
    }

    public void addUser(Person user) {
        personService.addUser(user);
        log.info("Пользователь добавлен в бд");
    }

    public boolean checkPersonBySocialNetworksId(String socType, Integer socId) {
        Person checkPerson = personService.getUserBySocialNetworksId(socType, socId);
        if (checkPerson != null) {
            log.info("Пользователь уже есть в бд");
            return true;
        }
        log.info("Пользователя нет в бд");
        return false;
    }

    public Person getPersonBySocialNetworksId(Person person) {
        for (String key : person.getSocialNetworks().keySet()) {
            Person getPerson = personService.getUserBySocialNetworksId(key, person.getSocialNetworks().get(key));
            if (getPerson != null) {
                log.info("Пользователь уже есть в бд");
                return getPerson;
            }
        }
        return null;
    }

    public Person getUserById(Long id) {
        return personService.getUserByID(id);
    }

    public Person getUserBySocialId(String type, Integer id) {
        return personService.getUserBySocialNetworksId(type, id);
    }

}
