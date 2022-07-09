package dev.struchkov.autoresponder;

import dev.struchkov.autoresponder.compare.UnitPriorityComparator;
import dev.struchkov.autoresponder.entity.KeyWord;
import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static dev.struchkov.autoresponder.util.Parser.splitWords;
import static dev.struchkov.haiti.utils.Exceptions.utilityClass;
import static dev.struchkov.haiti.utils.Inspector.isNotNull;

/**
 * Реализуют основную логику автоответчика.
 *
 * @author upagge [07/07/2019]
 */
public final class Responder {

    private static final Logger log = LoggerFactory.getLogger(Responder.class);

    /**
     * Компоратор для сортировки Unit-ов
     */
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    private Responder() {
        utilityClass();
    }

    /**
     * Выбирает, какой {@link Unit} будет отдан для обработки
     *
     * @param nextUnits Множество следующих Unit-ов
     * @param message   Запрос пользователя - текстовое сообщение
     * @return Юнит, который нуждается в обработке в соответствии с запросом пользователя
     */
    public static <U extends Unit<U>> Optional<U> nextUnit(String message, Collection<U> nextUnits) {
        isNotNull(nextUnits);
        final Set<U> searchUnit = new HashSet<>();

        if (message != null && nextUnits != null) {
            for (U unit : nextUnits) {
                final String unitPhrase = unit.getPhrase();
                if (
                        unitPhrase != null
                                && !unitPhrase.isEmpty()
                                && unitPhrase.equalsIgnoreCase(message)
                ) {
                    searchUnit.add(unit);
                }

                if (unit.getPattern() != null && patternReg(unit.getPattern(), message)) {
                    searchUnit.add(unit);
                }

                if (percentageMatch(unit, message) >= unit.getMatchThreshold()) {
                    searchUnit.add(unit);
                }
            }
        }

        if (searchUnit.isEmpty()) {
            for (U nextUnit : nextUnits) {
                if (isNotPattern(nextUnit) && isNotKeyWords(nextUnit) && isNotPhrase(nextUnit)) {
                    searchUnit.add(nextUnit);
                }
            }
        }

        return searchUnit.stream().max(UNIT_PRIORITY_COMPARATOR);
    }

    private static <U extends Unit<U>> boolean isNotPhrase(U nextUnit) {
        return nextUnit.getPhrase() == null;
    }

    private static <U extends Unit<U>> boolean isNotPattern(U nextUnit) {
        return nextUnit.getPattern() == null;
    }

    private static <U extends Unit<U>> boolean isNotKeyWords(U nextUnit) {
        return nextUnit.getKeyWords() == null || nextUnit.getKeyWords().isEmpty();
    }

    private static boolean patternReg(Pattern pattern, String message) {
        isNotNull(pattern);
        return message.matches(pattern.pattern());
    }

    private static <U extends Unit<U>> double percentageMatch(U unit, String message) {
        final Set<KeyWord> unitKeyWords = unit.getKeyWords();
        if (unitKeyWords != null && !unitKeyWords.isEmpty()) {
            final Set<String> messageWords = splitWords(message);
            final Set<KeyWord> intersection = getIntersection(unitKeyWords, messageWords);
            final double intersectionWeight = getIntersectionWeight(intersection);
            log.debug(Message.UNIT_KEYWORDS, unitKeyWords, unitKeyWords.size());
            log.debug(Message.USER_MESSAGE_KEYWORDS, messageWords);
            log.debug(Message.INTERSECTION, intersection, intersectionWeight);
            log.debug(Message.CROSSING_PERCENTAGE, intersectionWeight / (double) unitKeyWords.size() * 100.0, unit.getMatchThreshold());
            return (double) intersection.size() / (double) unitKeyWords.size() * 100.0;
        } else {
            return 0.0;
        }
    }

    private static double getIntersectionWeight(Set<KeyWord> intersection) {
        return intersection.stream().mapToInt(KeyWord::getImportant).sum();
    }

    private static Set<KeyWord> getIntersection(Set<KeyWord> unitKeyWords, Set<String> messageWords) {
        final Set<KeyWord> intersection = new HashSet<>();
        for (KeyWord unitKeyWord : unitKeyWords) {
            if (messageWords.contains(unitKeyWord.getWord())) {
                intersection.add(unitKeyWord);
            }
        }
        return intersection;
    }

}
