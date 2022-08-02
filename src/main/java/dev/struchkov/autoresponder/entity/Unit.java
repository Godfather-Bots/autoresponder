package dev.struchkov.autoresponder.entity;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Абстрактная сущность, отвечающая за хранение данных, необходимая для обработки запроса.
 *
 * @author upagge [07/07/2019]
 */
public abstract class Unit<U extends Unit<U, M>, M extends DeliverableText> {

    /**
     * Ключевые слова.
     */
    protected Set<KeyWord> triggerWords;

    /**
     * Точная фраза.
     */
    protected Set<String> phrases;

    /**
     * Триггеры на срабатывание юнита по регулярному выражению.
     */
    protected Set<Pattern> triggerPatterns;

    /**
     * Пользовательский триггер
     */
    protected Predicate<M> triggerCheck;

    /**
     * Значение минимального отношения количества найденных ключевых слов, к количеству ключевых слов Unit-а.
     */
    protected Integer matchThreshold;

    /**
     * Значение приоритета.
     */
    protected Integer priority;

    /**
     * Множество следующих Unit в сценарии.
     */
    protected Set<U> nextUnits;

    protected Unit(
            Set<KeyWord> triggerWords,
            Set<String> phrases,
            Predicate<M> triggerCheck,
            Set<Pattern> triggerPatterns,
            Integer matchThreshold,
            Integer priority,
            Set<U> nextUnits
    ) {
        this.triggerWords = triggerWords;
        this.phrases = phrases;
        this.triggerCheck = triggerCheck;
        this.triggerPatterns = triggerPatterns;
        this.matchThreshold = matchThreshold == null ? 10 : matchThreshold;
        this.priority = priority == null ? 10 : priority;
        this.nextUnits = nextUnits;
    }

    public Set<KeyWord> getTriggerWords() {
        return triggerWords;
    }

    public void setTriggerWords(Set<KeyWord> triggerWords) {
        this.triggerWords = triggerWords;
    }

    public Set<String> getPhrases() {
        return phrases;
    }

    public void addPhrase(String phrase) {
        phrases.add(phrase);
    }

    public void addPhrases(Collection<String> phrases) {
        this.phrases.addAll(phrases);
    }

    public void setPhrases(Set<String> phrases) {
        this.phrases = phrases;
    }

    public Set<Pattern> getTriggerPatterns() {
        return triggerPatterns;
    }

    public void setTriggerPatterns(Set<Pattern> triggerPatterns) {
        this.triggerPatterns = triggerPatterns;
    }

    public Integer getMatchThreshold() {
        return matchThreshold;
    }

    public void setMatchThreshold(Integer matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<U> getNextUnits() {
        return nextUnits;
    }

    public void setNextUnits(Set<U> nextUnits) {
        this.nextUnits = nextUnits;
    }

    public Predicate<M> getTriggerCheck() {
        return triggerCheck;
    }

    public void setTriggerCheck(Predicate<M> triggerCheck) {
        this.triggerCheck = triggerCheck;
    }

}
