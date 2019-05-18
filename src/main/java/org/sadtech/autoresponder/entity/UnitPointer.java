package org.sadtech.autoresponder.entity;

import java.util.Objects;

/*
    Сохраняет юнит, на котором остановился пользователь.
 */
public class UnitPointer {

    private Integer entityId;
    private Unit unit;

    public UnitPointer(Integer entityId, Unit unit) {
        this.entityId = entityId;
        this.unit = unit;
    }

    public UnitPointer(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPointer unitPointer = (UnitPointer) o;
        return Objects.equals(entityId, unitPointer.entityId) &&
                Objects.equals(unit, unitPointer.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, unit);
    }

    @Override
    public String toString() {
        return "UnitPointer{" +
                "entityId=" + entityId +
                ", unit=" + unit +
                '}';
    }
}
