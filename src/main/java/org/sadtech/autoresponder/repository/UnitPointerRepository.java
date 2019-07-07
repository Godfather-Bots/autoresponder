package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;

/**
 * Интегрфейс для работы с хранилищем сущности {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerRepository {

    void add(UnitPointer unitPointer);

    void edit(UnitPointer unitPointer);

    void remove(Integer entityId);

    void addAll(Collection<UnitPointer> unitPointers);

    /**
     * @param entityId Идентификатор пользователя
     * @return Объект с последним обработанным {@link org.sadtech.autoresponder.entity.Unit} для пользователя
     */
    UnitPointer findByEntityId(Integer entityId);

}
