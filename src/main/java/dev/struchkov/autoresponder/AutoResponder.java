package dev.struchkov.autoresponder;

import dev.struchkov.autoresponder.compare.UnitPriorityComparator;
import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.entity.UnitPointer;
import dev.struchkov.autoresponder.service.UnitPointerService;
import dev.struchkov.autoresponder.util.Message;
import dev.struchkov.autoresponder.util.Parser;
import dev.struchkov.haiti.utils.Inspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static dev.struchkov.haiti.utils.Inspector.isEmpty;
import static dev.struchkov.haiti.utils.Inspector.isNotEmpty;
import static dev.struchkov.haiti.utils.Inspector.isNotNull;

/**
 * Реализуют основную логику автоответчика.
 *
 * @author upagge [07/07/2019]
 */
public class AutoResponder<U extends Unit<U>> {

    private static final Logger log = LoggerFactory.getLogger(AutoResponder.class);

    /**
     * Компоратор для сортировки Unit-ов
     */
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    /**
     * Начальные юниты, первый запрос приходит на них
     */
    private final Set<U> startUnits;

    private final UnitPointerService<U> unitPointerService;

    /**
     * Дефолтный юнит, отправляется если ни один Unit не подошел
     */
    private U defaultUnit;

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
    public Optional<U> answer(Long entityId, String message) {
        isNotNull(entityId, message);
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
            final Set<U> nextUnits = unitAnswer.getNextUnits();
            if (isEmpty(nextUnits)) {
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
    private Optional<U> nextUnit(Set<U> nextUnits, String message) {
        isNotNull(nextUnits, message);
        final Set<U> searchUnit = new HashSet<>();

        if (isNotEmpty(nextUnits)) {
            for (U unit : nextUnits) {
                if (unit.getPhrase() != null
                        && !unit.getPhrase().isEmpty()
                        && unit.getPhrase().equalsIgnoreCase(message)) {
                    searchUnit.add(unit);
                }

                if (unit.getPattern() != null && patternReg(unit, message)) {
                    searchUnit.add(unit);
                }

                if (percentageMatch(unit, Parser.parse(message)) >= unit.getMatchThreshold()) {
                    searchUnit.add(unit);
                }
            }
        }

        if (isEmpty(searchUnit)) {
            for (U nextUnit : nextUnits) {
                if ((nextUnit.getPattern() == null && (nextUnit.getKeyWords() == null || nextUnit.getKeyWords().isEmpty()))) {
                    searchUnit.add(nextUnit);
                }
            }
        }
        return searchUnit.stream().max(UNIT_PRIORITY_COMPARATOR);
    }

    private boolean patternReg(U unit, String message) {
        isNotNull(unit);
        return message.matches(unit.getPattern().pattern());
    }

    private Double percentageMatch(U unit, Set<String> words) {
        final Set<String> keyWords = unit.getKeyWords();
        if (keyWords != null && !keyWords.isEmpty()) {
            final Set<String> temp = new HashSet<>(keyWords);
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

    public UnitPointerService<U> getUnitPointerService() {
        return unitPointerService;
    }

    public Set<U> getStartUnits() {
        return startUnits;
    }

    public U getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(U defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

}
