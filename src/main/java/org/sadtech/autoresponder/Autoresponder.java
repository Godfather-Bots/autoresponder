package org.sadtech.autoresponder;

import org.sadtech.autoresponder.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
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

    /*
        Начальные unit, по которым происходит поиск, если пользователь только обратился или закончил сценарий.
     */
    private final Set<Unit> menuUnits;
    private final UnitPointerService unitPointerService;

    public Autoresponder(UnitPointerService unitPointerService, Set<Unit> menuUnits) {
        this.menuUnits = menuUnits;
        this.unitPointerService = unitPointerService;
    }

    public UnitPointerService getUnitPointerService() {
        return unitPointerService;
    }

    /*
        Возвращает unit на основании сообщения пользователя
     */
    public Unit answer(Integer personId, String message) {
        UnitPointer unitPointer = checkAndAddPerson(personId);
        Unit unit;
        if (unitPointer.getUnit() == null) {
            unit = nextUnit(menuUnits, message); // выбирает unit из menuUnits, если пользователь обращается впервые
        } else {
            if (unitPointer.getUnit().getNextUnits() == null) {
                unit = nextUnit(menuUnits, message); // если пользователь закончил сценарий, то выбирает следующий юнит из menuUnits
            } else {
                unit = nextUnit(unitPointer.getUnit().getNextUnits(), message);
            }
        }
        if (unit != null) {
            unitPointerService.edit(personId, unit);
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
            Optional<Unit> patternUnits = nextUnits.stream()
                    .filter(nextUnit -> nextUnit.getPattern() != null)
                    .filter(nextUnit -> patternReg(nextUnit, message))
                    .max(UNIT_PRIORITY_COMPARATOR);

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream()
                        .filter(nextUnit -> textPercentageMatch(nextUnit, Parser.parse(message)) >= nextUnit.getMatchThreshold())
                        .max(UNIT_PRIORITY_COMPARATOR);
            }

            if (!patternUnits.isPresent()) {
                patternUnits = nextUnits.stream()
                        .filter(nextUnit -> (nextUnit.getPattern() == null && nextUnit.getKeyWords() == null))
                        .max(UNIT_PRIORITY_COMPARATOR);
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
