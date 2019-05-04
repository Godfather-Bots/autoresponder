package org.sadtech.autoresponder.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class Unit {

    private Set<String> keyWords;
    private Pattern pattern;
    private Integer matchThreshold = 10;
    private Integer priority = 10;
    private Set<Unit> nextUnits;

    public Unit() {

    }

    public Unit(Unit... nextUnit) {
        nextUnits = new HashSet(nextUnits);
    }

    public void setKeyWord(String keyWord) {
        if (keyWords == null) {
            keyWords = new HashSet<>();
        }
        keyWords.add(keyWord);
    }

    public void setNextUnit(Unit unit) {
        if (nextUnits == null) {
            nextUnits = new HashSet<>();
        }
        nextUnits.add(unit);
    }

    public Set<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<String> keyWords) {
        this.keyWords = keyWords;
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

    public Set<Unit> getNextUnits() {
        return nextUnits;
    }

    public void setNextUnits(Set<Unit> nextUnits) {
        this.nextUnits = nextUnits;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(keyWords, unit.keyWords) &&
                Objects.equals(pattern, unit.pattern) &&
                Objects.equals(matchThreshold, unit.matchThreshold) &&
                Objects.equals(priority, unit.priority) &&
                Objects.equals(nextUnits, unit.nextUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWords, pattern, matchThreshold, priority, nextUnits);
    }
}
