package org.sadtech.autoresponder;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.service.UnitPointerService;
import org.sadtech.autoresponder.util.Parser;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Autoresponder {

    private static final Logger log = Logger.getLogger(Autoresponder.class);

    private Set<Unit> menuUnits;
    private UnitPointerService unitPointerService;

    public Autoresponder(UnitPointerService unitPointerService) {
        this.unitPointerService = unitPointerService;
    }

    public void setMenuUnits(Set<Unit> menuUnits) {
        this.menuUnits = menuUnits;
    }

    public UnitPointerService getUnitPointerService() {
        return unitPointerService;
    }

    public void setUnitPointerService(UnitPointerService unitPointerService) {
        this.unitPointerService = unitPointerService;
    }

    public Unit answer(Integer idPerson, String message) {
        UnitPointer unitPointer = checkAndAddPerson(idPerson);
        Unit unit;
        if (unitPointer.getUnit() == null) {
            unit = nextUnit(menuUnits, message);
        } else {
            if (unitPointer.getUnit().getNextUnits() == null) {
                unit = nextUnit(menuUnits, message);
            } else {
                unit = nextUnit(unitPointer.getUnit().getNextUnits(), message);
            }
        }
        if (unit != null) {
            unitPointer.setUnit(unit);
        }
        return unit;
    }

    private UnitPointer checkAndAddPerson(Integer idPerson) {
        UnitPointer unitPointer;
        if (unitPointerService.check(idPerson)) {
            unitPointer = unitPointerService.getByEntityId(idPerson);
        } else {
            unitPointer = new UnitPointer(idPerson);
            unitPointerService.add(unitPointer);
        }
        return unitPointer;
    }

    private Unit nextUnit(Set<Unit> nextUnits, String message) {
        if (nextUnits.size() > 0) {
            UnitPriorityComparator unitPriorityComparator = new UnitPriorityComparator();
            Optional<Unit> patternUnits = nextUnits.stream().filter(nextUnit -> nextUnit.getPattern() != null).filter(nextUnit -> patternReg(nextUnit, message)).max(unitPriorityComparator);

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream()
                        .filter(nextUnit -> textPercentageMatch(nextUnit, Parser.parse(message)) >= nextUnit.getMatchThreshold())
                        .max(unitPriorityComparator);
            }

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream()
                        .filter(nextUnit -> (nextUnit.getPattern() == null && nextUnit.getKeyWords() == null))
                        .max(unitPriorityComparator);
            }

            return patternUnits.orElse(null);
        } else {
            return null;
        }
    }

    private boolean patternReg(Unit unit, String message) {
        Pattern pattern = unit.getPattern();
        Matcher m = pattern.matcher(message);
        return m.find();
    }

    private Double textPercentageMatch(Unit unit, Set<String> words) {
        if (unit.getKeyWords() != null) {
            Set<String> temp = new HashSet<>(unit.getKeyWords());
            temp.retainAll(words);
            log.info("Юнит: " + unit.getClass().getSimpleName());
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
