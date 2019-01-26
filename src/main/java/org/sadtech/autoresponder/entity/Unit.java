package org.sadtech.autoresponder.entity;

import java.util.*;

public abstract class Unit {

    private Set<String> keyWords;
    private Integer matchThreshold;
    private Integer priority;
    private List<Unit> nextUnits;

    public Unit() {
        priority = 10;
        matchThreshold = 50;
    }

    public Unit(Set<String> keyWords, Integer matchThreshold, Integer priority, Boolean level, List<Unit> nextUnits) {
        this.keyWords = keyWords;
        this.matchThreshold = matchThreshold;
        this.priority = priority;
        this.nextUnits = nextUnits;
    }

    public void setKeyWord(String keyWord) {
        if (keyWords == null) {
            keyWords = new HashSet<>();
        }
        keyWords.add(keyWord);
    }

    public void setNextUnit(Unit unit) {
        if (nextUnits == null) {
            nextUnits = new ArrayList<>();
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

    public List<Unit> getNextUnits() {
        return nextUnits;
    }

    public void setNextUnits(List<Unit> nextUnits) {
        this.nextUnits = nextUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(keyWords, unit.keyWords) &&
                Objects.equals(matchThreshold, unit.matchThreshold) &&
                Objects.equals(priority, unit.priority) &&
                Objects.equals(nextUnits, unit.nextUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWords, matchThreshold, priority, nextUnits);
    }
}
