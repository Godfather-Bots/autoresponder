package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

/**
 * Сервис для взаимодействия с сущностью {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerService<U extends Unit> {

    void add(UnitPointer<U> unitPointer);

    /**
     * Проверка наличия {@link UnitPointer} для пользователя
     *
     * @param entityId Идентификатор пользователя
     * @return true - если найдено
     */
    boolean check(Integer entityId);

    UnitPointer<U> getByEntityId(Integer entityId);

    void edit(Integer personId, U unit);

}
