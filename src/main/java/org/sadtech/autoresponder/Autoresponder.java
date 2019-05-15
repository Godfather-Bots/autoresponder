package org.sadtech.autoresponder;

import org.sadtech.autoresponder.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.exception.NotFoundUnitException;
import org.sadtech.autoresponder.service.UnitPointerService;
import org.sadtech.autoresponder.util.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Отвечает за функционирование автоответчика
 */
public class Autoresponder {

    private static final Logger log = LoggerFactory.getLogger(Autoresponder.class);
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    private final Set<Unit> startUnits;
    private Unit defaultUnit;
    private final UnitPointerService unitPointerService;

    public Autoresponder(UnitPointerService unitPointerService, Set<Unit> startUnits) {
        this.startUnits = startUnits;
        this.unitPointerService = unitPointerService;
    }

    public UnitPointerService getUnitPointerService() {
        return unitPointerService;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public Unit answer(Integer personId, String message) {
        UnitPointer unitPointer = checkAndAddPerson(personId);
        Unit unit;
        try {
            if (unitPointer.getUnit() == null || unitPointer.getUnit().getNextUnits() == null) {
                unit = nextUnit(startUnits, message); // выбирает unit из startUnits, если пользователь обращается впервые
            } else {
                unit = nextUnit(unitPointer.getUnit().getNextUnits(), message);
            }
            unitPointerService.edit(personId, unit);
        } catch (NotFoundUnitException e) {
            unit = defaultUnit;
        }
        return unit;
    }

    private Unit nextUnit(Set<Unit> nextUnits, String message) {
        Optional<Unit> unit = nextUnits.stream()
                .filter(nextUnit -> nextUnit.getPattern() != null)
                .filter(nextUnit -> patternReg(nextUnit, message))
                .max(UNIT_PRIORITY_COMPARATOR);

        if (!unit.isPresent()) {
            unit = nextUnits.stream()
                    .filter(nextUnit -> textPercentageMatch(nextUnit, Parser.parse(message)) >= nextUnit.getMatchThreshold())
                    .max(UNIT_PRIORITY_COMPARATOR);
        }

        if (!unit.isPresent()) {
            unit = nextUnits.stream()
                    .filter(nextUnit -> (nextUnit.getPattern() == null && nextUnit.getKeyWords() == null))
                    .max(UNIT_PRIORITY_COMPARATOR);
        }
        return unit.orElseThrow(NotFoundUnitException::new);
    }

    private UnitPointer checkAndAddPerson(Integer personId) {
        UnitPointer unitPointer;
        if (unitPointerService.check(personId)) {
            unitPointer = unitPointerService.getByEntityId(personId);
        } else {
            unitPointer = new UnitPointer(personId);
            unitPointerService.add(unitPointer);
        }
        return unitPointer;
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
