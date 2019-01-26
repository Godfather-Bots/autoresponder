package org.sadtech.autoresponder;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.service.PersonService;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.Parser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Autoresponder {

    public static final Logger log = Logger.getLogger(Autoresponder.class);

    private UnitService unitService;
    private PersonService personService;

    public Autoresponder(UnitService unitService, PersonService personService) {
        this.unitService = unitService;
        this.personService = personService;
    }

    public Unit answer(Integer idPerson, String message) {
        Person person = checkAndAddPerson(idPerson);
        Unit unit;
        if (person.getUnit() == null) {
            unit = nextUnit(unitService.menuUnit(), message);
        } else {
            if (person.getUnit().getNextUnits() == null) {
                unit = nextUnit(unitService.menuUnit(), message);
            } else {
                unit = nextUnit(person.getUnit().getNextUnits(), message);
            }
        }
        if (unit != null) {
            person.setUnit(unit);
        }
        return unit;
    }

    private Person checkAndAddPerson(Integer idPerson) {
        Person person;
        if (personService.checkPerson(idPerson)) {
            person = personService.getPersonById(idPerson);
        } else {
            person = new Person(idPerson);
            personService.addPerson(person);
        }
        return person;
    }

    private Unit nextUnit(List<Unit> nextUnits, String message) {
        if (nextUnits.size() > 0) {
            Parser parser = new Parser();
            parser.setText(message);
            parser.parse();
            Optional<Unit> max = nextUnits.stream().filter(nextUnit -> textPercentageMatch(nextUnit, parser.getWords()) >= nextUnit.getMatchThreshold() || nextUnit.getKeyWords() == null).max(new UnitPriorityComparator());
            return max.orElse(null);
        } else {
            return null;
        }
    }

    private Double textPercentageMatch(Unit unit, Set<String> words) {
        if (unit.getKeyWords() != null) {
            Set<String> temp = new HashSet<>(unit.getKeyWords());
            temp.retainAll(words);
            log.info((temp.size() / unit.getKeyWords().size()) * 100);
            return (double) (temp.size() / unit.getKeyWords().size()) * 100;
        } else {
            return 0.0;
        }
    }

}
