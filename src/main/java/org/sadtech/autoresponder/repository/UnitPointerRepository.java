package org.sadtech.autoresponder.repository;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;
import java.util.Optional;

/**
 * Интегрфейс для работы с хранилищем сущности {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerRepository {

    UnitPointer add(@NonNull UnitPointer unitPointer);

    void edit(@NonNull UnitPointer unitPointer);

    void remove(@NonNull Integer entityId);

    void addAll(@NonNull Collection<UnitPointer> unitPointers);

    /**
     * @param entityId Идентификатор пользователя
     * @return Объект с последним обработанным {@link Unit} для пользователя
     */
    Optional<UnitPointer> findByEntityId(@NonNull Long entityId);

}
