package dev.struchkov.autoresponder;

import dev.struchkov.autoresponder.compare.UnitPriorityComparator;
import dev.struchkov.autoresponder.entity.DeliverableText;
import dev.struchkov.autoresponder.entity.KeyWord;
import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static dev.struchkov.autoresponder.util.Parser.splitWords;
import static dev.struchkov.haiti.utils.Checker.checkEmpty;
import static dev.struchkov.haiti.utils.Checker.checkNotEmpty;
import static dev.struchkov.haiti.utils.Checker.checkNotNull;
import static dev.struchkov.haiti.utils.Checker.checkNull;
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
     * Компаратор для сортировки Unit-ов
     */
    private static final UnitPriorityComparator UNIT_PRIORITY_COMPARATOR = new UnitPriorityComparator();

    private Responder() {
        utilityClass();
    }

    public static <U extends Unit<U, DeliverableText>> Optional<U> nextUnit(String message, Collection<U> nextUnits) {
        return nextUnit(() -> message, nextUnits);
    }

    /**
     * Выбирает, какой {@link Unit} будет отдан для обработки
     *
     * @param nextUnits Множество следующих Unit-ов
     * @param message   Запрос пользователя - текстовое сообщение
     * @return Юнит, который нуждается в обработке в соответствии с запросом пользователя
     */
    public static <M extends DeliverableText, U extends Unit<U, M>> Optional<U> nextUnit(M message, Collection<U> nextUnits) {
        isNotNull(nextUnits);
        final Set<U> searchUnit = new HashSet<>();

        if (checkNotEmpty(nextUnits)) {
            for (U unit : nextUnits) {
                final String text = message.getText();
                if (checkNotNull(text)) {
                    final Set<String> unitPhrases = unit.getPhrases();
                    if (checkNotEmpty(unitPhrases) && unitPhrases.contains(text)) {
                        searchUnit.add(unit);
                    }

                    final Set<Pattern> patterns = unit.getTriggerPatterns();
                    if (checkNotEmpty(patterns)) {
                        for (Pattern pattern : patterns) {
                            if (patternReg(pattern, text)) {
                                searchUnit.add(unit);
                                break;
                            }
                        }
                    }

                    if (percentageMatch(unit, text) >= unit.getMatchThreshold()) {
                        searchUnit.add(unit);
                    }
                }

                final Predicate<M> triggerCheck = unit.getTriggerCheck();
                if (checkNotNull(triggerCheck) && triggerCheck.test(message)) {
                    searchUnit.add(unit);
                }
            }
        }

        if (searchUnit.isEmpty()) {
            for (U nextUnit : nextUnits) {
                if (isNotTrigger(nextUnit)) {
                    searchUnit.add(nextUnit);
                }
            }
        }

        return searchUnit.stream().max(UNIT_PRIORITY_COMPARATOR);
    }

    private static <U extends Unit<U, ?>> boolean isNotTrigger(U nextUnit) {
        return isNotPattern(nextUnit) && isNotKeyWords(nextUnit) && isNotPhrase(nextUnit) && isNotCheck(nextUnit);
    }

    private static <U extends Unit<U, ?>> boolean isNotCheck(U unit) {
        return checkNull(unit.getTriggerCheck());
    }

    private static <U extends Unit<U, ?>> boolean isNotPhrase(U unit) {
        return checkEmpty(unit.getPhrases());
    }

    private static <U extends Unit<U, ?>> boolean isNotPattern(U unit) {
        return checkEmpty(unit.getTriggerPatterns());
    }

    private static <U extends Unit<U, ?>> boolean isNotKeyWords(U unit) {
        return checkEmpty(unit.getTriggerWords());
    }

    private static boolean patternReg(Pattern pattern, String message) {
        isNotNull(pattern);
        return message.matches(pattern.pattern());
    }

    private static <U extends Unit<U, ?>> double percentageMatch(U unit, String message) {
        final Set<KeyWord> unitKeyWords = unit.getTriggerWords();
        if (checkNotEmpty(unitKeyWords)) {
            final Set<String> messageWords = splitWords(message);
            final Set<KeyWord> intersection = getIntersection(unitKeyWords, messageWords);
            final double intersectionWeight = getIntersectionWeight(intersection);
            log.debug(Message.UNIT_KEYWORDS, unitKeyWords, unitKeyWords.size());
            log.debug(Message.USER_MESSAGE_KEYWORDS, messageWords);
            log.debug(Message.INTERSECTION, intersection, intersectionWeight);
            log.debug(Message.CROSSING_PERCENTAGE, intersectionWeight / unitKeyWords.size() * 100.0, unit.getMatchThreshold());
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
