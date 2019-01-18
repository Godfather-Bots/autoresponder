package org.sadtech.autoresponder;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.service.PersonService;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.insertwords.InsertWords;

import java.util.List;

@AllArgsConstructor
public class Autoresponder {

    private UnitService unitService;
    private PersonService personService;

    public String answer(@NonNull Integer idPerson, @NonNull String message) {
        Person person = CheckAndAddPerson(idPerson);
        Unit unit;
        if (person.getUnit() == null) {
            unit = unitService.nextUnit(unitService.menuUnit(), message);
        } else {
            unit = unitService.nextUnit(person.getUnit(), message);
        }
        person.setUnit(unit);
        return unit.getAnswer();
    }

    public String answer(@NonNull Integer idPerson, @NonNull String message, @NonNull List<String> words) {
        String answer = answer(idPerson, message);
        InsertWords insertWords = new InsertWords();
        insertWords.setInText(answer);
        insertWords.insert(words);
        return insertWords.getOutText();
    }

    private Person CheckAndAddPerson(Integer idPerson) {
        Person person;
        if (personService.checkPerson(idPerson)) {
            person = personService.getPersonById(idPerson);
        } else {
            person = new Person();
            person.setId(idPerson);
            personService.addPerson(person);
        }
        return person;
    }

}
