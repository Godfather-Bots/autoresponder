package org.sadtech.autoresponder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.sadtech.autoresponder.util.Description;

/**
 * Сущность для сохранения позиции пользователя в сценарии, состоящем из связного списка элементов {@link Unit}.
 *
 * @author upagge [07/07/2019]
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UnitPointer {

    @Description("Идентификатор пользователя")
    private final Long entityId;

    @Description("Юнит, который был обработан")
    private Unit unit;

}
