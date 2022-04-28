package dev.struchkov.autoresponder.entity;

import java.util.Objects;

/**
 * Сущность для сохранения позиции пользователя в сценарии, состоящем из связного списка элементов {@link Unit}.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointer<U extends Unit<U>> {

    /**
     * Идентификатор пользователя.
     */
    private Long entityId;

    /**
     * Юнит, который был обработан.
     */
    private U unit;

    public UnitPointer(Long entityId, U unit) {
        this.entityId = entityId;
        this.unit = unit;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public U getUnit() {
        return unit;
    }

    public void setUnit(U unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPointer<?> that = (UnitPointer<?>) o;
        return Objects.equals(entityId, that.entityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }

}
