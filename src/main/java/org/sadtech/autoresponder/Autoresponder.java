package org.sadtech.autoresponder;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.service.PersonService;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.InsertWords;

import java.util.List;

@AllArgsConstructor
public class Autoresponder {

    private UnitService unitService;
    private PersonService personService;

    public String answer(@NotNull Integer idPerson, @NotNull String message) {
        Person person = personService.getPersonById(idPerson);
        Unit unit = person.getUnit();
        unit = unitService.nextUnit(unit, message);
        person.setUnit(unit);
        return unit.getAnswer();
    }

    public String answer(@NotNull Integer idPerson, @NotNull String message, @NotNull List<String> words) {
        Person person = personService.getPersonById(idPerson);
        Unit unit = unitService.nextUnit(person.getUnit(), message);
        InsertWords insertWords = new InsertWords();
        insertWords.setInText(unit.getAnswer());
        insertWords.insert(words);
        return insertWords.getOutText();
    }

}
