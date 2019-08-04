package org.sadtech.autoresponder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sadtech.autoresponder.util.Description;

/**
 * Сущность для сохранения позиции пользователя в сценарии, состоящем из связного списка элементов {@link Unit}.
 *
 * @author upagge [07/07/2019]
 */
@Data
@AllArgsConstructor
public class UnitPointer<U extends Unit> {

    @Description("Идентификатор пользователя")
    private Integer entityId;

    @Description("Юнит, который был обработан")
    private U unit;

    public UnitPointer(Integer entityId) {
        this.entityId = entityId;
    }

}
