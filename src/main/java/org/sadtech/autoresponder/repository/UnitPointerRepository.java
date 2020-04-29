package org.sadtech.autoresponder.repository;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Optional;

/**
 * Интегрфейс для работы с хранилищем сущности {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerRepository<U extends Unit> {

    UnitPointer<U> save(@NonNull UnitPointer<U> unitPointer);

    /**
     * @param entityId Идентификатор пользователя
     * @return Объект с последним обработанным {@link Unit} для пользователя
     */
    Optional<UnitPointer<U>> findByEntityId(@NonNull Long entityId);

    void removeByEntityId(@NonNull Long entityId);

}
