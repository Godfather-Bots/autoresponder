package org.sadtech.autoresponder;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.service.PersonService;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.Parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Autoresponder {

    public static final Logger log = Logger.getLogger(Autoresponder.class);

    private UnitService unitService;

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

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

    private Unit nextUnit(Set<Unit> nextUnits, String message) {
        if (nextUnits.size() > 0) {
            UnitPriorityComparator unitPriorityComparator = new UnitPriorityComparator();
            Optional<Unit> patternUnits = nextUnits.stream().filter(nextUnit -> nextUnit.getPattern() != null).filter(nextUnit -> patternReg(nextUnit, message)).max(unitPriorityComparator);

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream().filter(nextUnit -> (textPercentageMatch(nextUnit, new HashSet<>(Collections.singleton(message))) == 100.0)).max(unitPriorityComparator);
                if (!patternUnits.isPresent()) {
                    Parser parser = new Parser();
                    parser.setText(message);
                    parser.parse();
                    patternUnits = nextUnits.stream().filter(nextUnit -> textPercentageMatch(nextUnit, parser.getWords()) >= nextUnit.getMatchThreshold()).max(unitPriorityComparator);
                }
            }

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream().filter(nextUnit -> (nextUnit.getPattern() == null && nextUnit.getKeyWords() == null)).max(unitPriorityComparator);
            }

            return patternUnits.orElse(null);
        } else {
            return null;
        }
    }

    private boolean patternReg(Unit unit, String message) {
        Pattern pattern = unit.getPattern();
        Matcher m = pattern.matcher(message);
        while (m.find()) {
            return true;
        }
        return false;
    }

    private Double textPercentageMatch(Unit unit, Set<String> words) {
        if (unit.getKeyWords() != null) {
            Set<String> temp = new HashSet<>(unit.getKeyWords());
            temp.retainAll(words);
            log.info("Ключевые слова юнита: " + unit.getKeyWords() + " (" + unit.getKeyWords().size() + ")");
            log.info("Ключевые слова от пользователя: " + words);
            log.info("Пересечение: " + temp + " (" + temp.size() + ")");
            log.info("Процент: " + (double) temp.size() / (double) unit.getKeyWords().size() * 100.0 + " Необходимо: " + unit.getMatchThreshold());
            return (double) temp.size() / (double) unit.getKeyWords().size() * 100.0;
        } else {
            return 0.0;
        }
    }

}
