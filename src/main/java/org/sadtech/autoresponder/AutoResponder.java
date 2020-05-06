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
import org.sadtech.autoresponder.util.Message;
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
    private final UnitPointerService<U> unitPointerService;

    public AutoResponder(UnitPointerService<U> unitPointerService, Set<U> startUnits) {
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
    public Optional<U> answer(@NonNull Long entityId, @NonNull String message) {
        Optional<UnitPointer<U>> unitPointer = unitPointerService.getByEntityId(entityId);
        final Optional<U> answer = nextUnit(
                unitPointer.isPresent()
                        && unitPointer.get().getUnit().getNextUnits() != null
                        && !unitPointer.get().getUnit().getNextUnits().isEmpty()
                        ? unitPointer.get().getUnit().getNextUnits()
                        : startUnits,
                message
        );
        if (answer.isPresent()) {
            final U unitAnswer = answer.get();
            if (unitAnswer.getNextUnits().isEmpty()) {
                unitPointerService.removeByEntityId(entityId);
            } else {
                unitPointerService.save(new UnitPointer<>(entityId, unitAnswer));
            }
            return answer;
        } else {
            return Optional.ofNullable(defaultUnit);
        }
    }

    /**
     * Выбирает, какой {@link Unit} будет отдан для обработки
     *
     * @param nextUnits Множество следующих Unit-ов
     * @param message   Запрос пользователя - текстовое сообщение
     * @return Юнит, который нуждается в обработке в соответствии с запросом пользователя
     */
    private Optional<U> nextUnit(@NonNull Set<U> nextUnits, @NonNull String message) {
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
            log.trace(Message.UNIT_KEYWORDS, keyWords, keyWords.size());
            log.trace(Message.USER_MESSAGE_KEYWORDS, words);
            log.trace(Message.INTERSECTION, temp, temp.size());
            log.trace(Message.CROSSING_PERCENTAGE, (double) temp.size() / (double) keyWords.size() * 100.0, unit.getMatchThreshold());
            return (double) temp.size() / (double) keyWords.size() * 100.0;
        } else {
            return 0.0;
        }
    }

}
