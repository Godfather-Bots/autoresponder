package org.sadtech.autoresponder;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.autoresponder.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.service.UnitPointerService;
import org.sadtech.autoresponder.util.Description;
import org.sadtech.autoresponder.util.Parser;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Реализуют основную логику автоответчика.
 *
 * @author upagge [07/07/2019]
 */
@Slf4j
public class AutoResponder<U extends Unit> {

    @Description("Компоратор для сортировки Unit-ов")
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    @Description("Начальные юниты, первый запрос приходит на них")
    private final Set<U> startUnits;

    @Getter
    @Setter
    @Description("Дефолтный юнит, отправляется если ни один Unit не подошел")
    private U defaultUnit;

    @Getter
    private final UnitPointerService unitPointerService;

    public AutoResponder(UnitPointerService unitPointerService, Set<U> startUnits) {
        this.startUnits = startUnits;
        this.unitPointerService = unitPointerService;
    }

    /**
     * Принимает текстовый запрос пользователя и отдает юнит, который соответствует запросу
     *
     * @param entityId Идентификатор клиента
     * @param message  Запрос пользователя - текстовое сообщение
     * @return {@link Unit}, который отвечает за данные для обработки данного запроса
     */
    public Optional<U> answer(@NonNull Long entityId, String message) {
        Optional<UnitPointer> unitPointer = unitPointerService.getByEntityId(entityId);
        Optional<U> unitOpt = nextUnit(
                !unitPointer.isPresent() || newScenario(unitPointer.get()) ? startUnits : unitPointer.get().getUnit().getNextUnits(),
                message
        );
        if (unitOpt.isPresent()) {
            U unit = unitOpt.get();
            if (unitPointer.isPresent()) {
                unitPointerService.edit(entityId, unit);
            } else {
                unitPointerService.add(new UnitPointer(entityId, unit));
            }
        }
        return unitOpt.isPresent() ? unitOpt : Optional.ofNullable(defaultUnit);
    }

    private boolean newScenario(UnitPointer unitPointer) {
        return unitPointer.getUnit() == null || unitPointer.getUnit().getNextUnits() == null || unitPointer.getUnit().getNextUnits().isEmpty();
    }

    /**
     * Выбирает, какой {@link Unit} будет отдан для обработки
     *
     * @param nextUnits Множество следующих Unit-ов
     * @param message   Запрос пользователя - текстовое сообщение
     * @return Юнит, который нуждается в обработке в соответствии с запросом пользователя
     */
    private Optional<U> nextUnit(@NonNull Set<U> nextUnits, String message) {
        Set<U> searchUnit = new HashSet<>();

        for (U unit : nextUnits) {
            if (unit.getPhrase() != null
                    && !unit.getPhrase().isEmpty()
                    && unit.getPhrase().equalsIgnoreCase(message)) {
                searchUnit.add(unit);
            }
        }

        for (U unit : nextUnits) {
            if (unit.getPattern() != null && patternReg(unit, message)) {
                searchUnit.add(unit);
            }
        }

        for (U unit : nextUnits) {
            if (percentageMatch(unit, Parser.parse(message)) >= unit.getMatchThreshold()) {
                searchUnit.add(unit);
            }
        }

        if (searchUnit.isEmpty()) {
            for (U nextUnit : nextUnits) {
                if ((nextUnit.getPattern() == null && (nextUnit.getKeyWords() == null || nextUnit.getKeyWords().isEmpty()))) {
                    searchUnit.add(nextUnit);
                }
            }
        }
        return searchUnit.stream().max(UNIT_PRIORITY_COMPARATOR);
    }


    private boolean patternReg(@NonNull U unit, String message) {
        return message.matches(unit.getPattern().pattern());
    }

    private Double percentageMatch(U unit, Set<String> words) {
        Set<String> keyWords = unit.getKeyWords();
        if (keyWords != null && !keyWords.isEmpty()) {
            Set<String> temp = new HashSet<>(keyWords);
            temp.retainAll(words);
            log.info("Ключевые слова юнита: {} ({})", keyWords, keyWords.size());
            log.info("Ключевые слова от пользователя: {}", words);
            log.info("Пересечение: {} ({})", temp, temp.size());
            log.info("Процент: {} Необходимо: {}", (double) temp.size() / (double) keyWords.size() * 100.0, unit.getMatchThreshold());
            return (double) temp.size() / (double) keyWords.size() * 100.0;
        } else {
            return 0.0;
        }
    }

}
