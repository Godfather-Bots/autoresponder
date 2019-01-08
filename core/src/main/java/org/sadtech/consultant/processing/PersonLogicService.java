package org.sadtech.consultant.processing;

import org.sadtech.consultant.database.entity.Person;
import org.sadtech.consultant.database.service.PersonService;
import org.springframework.stereotype.Component;

@Component
public class PersonLogicService {

    private PersonService personService;

    public PersonLogicService(PersonService userService) {
        this.personService = userService;
    }

    public boolean addUser(Person user) {
        personService.addUser(user);
        return true;
    }

    public Person getUserById(Long id) {
        return personService.getUserByID(id);
    }

    public Person getUserBySocialId(String type, Integer id) {
        return personService.getUserBySocialNetworksId(type, id);
    }

}
