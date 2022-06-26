package dev.struchkov.autoresponder;

import dev.struchkov.autoresponder.compare.UnitPriorityComparator;
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
        isNotNull(nextUnits, message);
        final Set<U> searchUnit = new HashSet<>();

        if (nextUnits != null) {
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

                if (percentageMatch(unit, splitWords(message)) >= unit.getMatchThreshold()) {
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
        }

        return searchUnit.stream().max(UNIT_PRIORITY_COMPARATOR);
    }

    private static boolean patternReg(Pattern pattern, String message) {
        isNotNull(pattern);
        return message.matches(pattern.pattern());
    }

    private static <U extends Unit<U>> Double percentageMatch(U unit, Set<String> words) {
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

}
