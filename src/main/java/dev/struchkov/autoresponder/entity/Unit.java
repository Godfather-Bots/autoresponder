package dev.struchkov.autoresponder.entity;

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
    protected Set<String> keyWords = new HashSet<>();

    /**
     * Точная фраза.
     */
    protected String phrase;

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
    protected Set<U> nextUnits = new HashSet<>();

    protected Unit(Set<String> keyWords,
                   String phrase,
                   Pattern pattern,
                   Integer matchThreshold,
                   Integer priority,
                   Set<U> nextUnits) {
        this.keyWords = keyWords;
        this.phrase = phrase;
        this.pattern = pattern;
        this.matchThreshold = matchThreshold == null ? 10 : matchThreshold;
        this.priority = priority == null ? 10 : priority;
        this.nextUnits = nextUnits;
    }

    public Set<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<String> keyWords) {
        this.keyWords = keyWords;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
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
        return Objects.equals(keyWords, unit.keyWords) && Objects.equals(phrase, unit.phrase) && Objects.equals(pattern, unit.pattern) && Objects.equals(matchThreshold, unit.matchThreshold) && Objects.equals(priority, unit.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWords, phrase, pattern, matchThreshold, priority);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "keyWords=" + keyWords +
                ", phrase='" + phrase + '\'' +
                ", pattern=" + pattern +
                ", matchThreshold=" + matchThreshold +
                ", priority=" + priority +
                '}';
    }

}
