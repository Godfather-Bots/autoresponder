package org.sadtech.autoresponder.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/*
     Абстрактный класс, предпологающий какие-то действия
 */
public abstract class Unit {

    private Set<String> keyWords;
    private Pattern pattern;
    private Integer matchThreshold = 10;
    private Integer priority = 10;
    private Set<Unit> nextUnits;

    public void setKeyWord(String... keyWord) {
        if (this.keyWords == null) {
            this.keyWords = new HashSet<>();
        }
        this.keyWords.addAll(Arrays.asList(keyWord));
    }

    public void setKeyWords(Set<String> keyWords) {
        if (this.keyWords == null) {
            this.keyWords = new HashSet<>();
        }
        this.keyWords.addAll(keyWords);
    }

    public Set<String> getKeyWords() {
        return keyWords;
    }

    public void setNextUnit(Unit... unit) {
        if (nextUnits == null) {
            nextUnits = new HashSet<>();
        }
        nextUnits.addAll(Arrays.asList(unit));
    }

    public void setNextUnits(Set<Unit> nextUnits) {
        if (nextUnits == null) {
            nextUnits = new HashSet<>();
        }
        this.nextUnits.addAll(nextUnits);
    }

    public Set<Unit> getNextUnits() {
        return nextUnits;
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

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
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
