package org.sadtech.autoresponder.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.sadtech.autoresponder.util.Description;

/**
 * Сущность для сохранения позиции пользователя в сценарии, состоящем из связного списка элементов {@link Unit}.
 *
 * @author upagge [07/07/2019]
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "entityId")
public class UnitPointer<U extends Unit> {

    @Description("Идентификатор пользователя")
    private Long entityId;

    @Description("Юнит, который был обработан")
    private U unit;

}
