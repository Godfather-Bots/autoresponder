package org.sadtech.autoresponder;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.autoresponder.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.exception.NotFoundUnitException;
import org.sadtech.autoresponder.service.UnitPointerService;
import org.sadtech.autoresponder.util.Description;
import org.sadtech.autoresponder.util.Parser;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализуют основную логику автоответчика.
 *
 * @author upagge [07/07/2019]
 */
@Slf4j
public class Autoresponder {

    @Description("Компоратор для сортировки Unit-ов")
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    @Description("Начальные юниты, первый запрос приходит на них")
    private final Set<Unit> startUnits;

    @Getter
    @Setter
    @Description("Дефолтный юнит, отправляется если ни один Unit не подошел")
    private Unit defaultUnit;

    @Getter
    private final UnitPointerService unitPointerService;

    public Autoresponder(UnitPointerService unitPointerService, Set<Unit> startUnits) {
        this.startUnits = startUnits;
        this.unitPointerService = unitPointerService;
    }

    /**
     * Принимает текстовый запрос пользователя и отдает юнит, который соответствует запросу
     *
     * @param personId Идентификатор пользователя
     * @param message  Запрос пользователя - текстовое сообщение
     * @return {@link Unit}, который отвечает за данные для обработки данного запроса
     */
    public Unit answer(@NonNull Integer personId, String message) {
        UnitPointer unitPointer = checkAndAddPerson(personId);
        Unit unit;
        try {
            if (unitPointer.getUnit() == null || unitPointer.getUnit().getNextUnits() == null) {
                unit = nextUnit(startUnits, message);
            } else {
                unit = nextUnit(unitPointer.getUnit().getNextUnits(), message);
            }
            unitPointerService.edit(personId, unit);
        } catch (NotFoundUnitException e) {
            unit = defaultUnit;
        }
        return Optional.ofNullable(unit).orElseThrow(NotFoundUnitException::new);
    }

    /**
     * Выбирает, какой {@link Unit} будет отдан для обработки
     *
     * @param nextUnits Множество следующих Unit-ов
     * @param message   Запрос пользователя - текстовое сообщение
     * @return Юнит, который нуждается в обработке в соответствии с запросом пользователя
     */
    private Unit nextUnit(Set<Unit> nextUnits, String message) {
        Optional<Unit> unit = nextUnits.stream()
                .filter(nextUnit -> nextUnit.getPattern() != null && patternReg(nextUnit, message))
                .max(UNIT_PRIORITY_COMPARATOR);

        if (!unit.isPresent()) {
            unit = nextUnits.stream()
                    .filter(nextUnit -> percentageMatch(nextUnit, Parser.parse(message)) >= nextUnit.getMatchThreshold())
                    .max(UNIT_PRIORITY_COMPARATOR);
        }

        if (!unit.isPresent()) {
            unit = nextUnits.stream()
                    .filter(nextUnit -> (nextUnit.getPattern() == null && nextUnit.getKeyWords() == null))
                    .max(UNIT_PRIORITY_COMPARATOR);
        }
        return unit.orElseThrow(NotFoundUnitException::new);
    }

    /**
     * Проверяет наличие {@link UnitPointer} у пользовтеля и создает его, если не находит
     *
     * @param personId Идентификатор пользователя
     * @return {@link UnitPointer}, который сохраняет {@link Unit}, который был обработан последним
     */
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

    private Double percentageMatch(Unit unit, Set<String> words) {
        if (unit.getKeyWords() != null) {
            Set<String> temp = new HashSet<>(unit.getKeyWords());
            temp.retainAll(words);
            log.info("Ключевые слова юнита: {} ({})", unit.getKeyWords(), unit.getKeyWords().size());
            log.info("Ключевые слова от пользователя: {}", words);
            log.info("Пересечение: {} ({})", temp, temp.size());
            log.info("Процент: {} Необходимо: {}", (double) temp.size() / (double) unit.getKeyWords().size() * 100.0, unit.getMatchThreshold());
            return (double) temp.size() / (double) unit.getKeyWords().size() * 100.0;
        } else {
            return 0.0;
        }
    }

}
