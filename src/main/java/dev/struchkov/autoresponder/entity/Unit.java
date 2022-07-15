package dev.struchkov.autoresponder.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Абстрактная сущность, отвечающая за хранение данных, необходимая для обработки запроса.
 *
 * @author upagge [07/07/2019]
 */
public abstract class Unit<U extends Unit<U>> {

    /**
     * Ключевые слова.
     */
    protected Set<KeyWord> keyWords;

    /**
     * Точная фраза.
     */
    protected Set<String> phrases;

    /**
     * Регулярное выражение.
     */
    protected Pattern pattern;

    /**
     * Значение минимального отношения количества найденых ключевых слов, к количеству ключевых слов Unit-а.
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
            Set<KeyWord> keyWords,
            Set<String> phrases,
            Pattern pattern,
            Integer matchThreshold,
            Integer priority,
            Set<U> nextUnits
    ) {
        this.keyWords = keyWords;
        this.phrases = phrases;
        this.pattern = pattern;
        this.matchThreshold = matchThreshold == null ? 10 : matchThreshold;
        this.priority = priority == null ? 10 : priority;
        this.nextUnits = nextUnits;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public Set<String> getPhrases() {
        return phrases;
    }

    public void addPhrase(String phrase) {
        phrases.add(phrase);
    }

    public void addPhrases(Collection<String> phrases) {
        phrases.addAll(phrases);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit<?> unit = (Unit<?>) o;
        return Objects.equals(keyWords, unit.keyWords) && Objects.equals(phrases, unit.phrases) && Objects.equals(pattern, unit.pattern) && Objects.equals(matchThreshold, unit.matchThreshold) && Objects.equals(priority, unit.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWords, phrases, pattern, matchThreshold, priority);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "keyWords=" + keyWords +
                ", phrases='" + phrases + '\'' +
                ", pattern=" + pattern +
                ", matchThreshold=" + matchThreshold +
                ", priority=" + priority +
                '}';
    }

}
