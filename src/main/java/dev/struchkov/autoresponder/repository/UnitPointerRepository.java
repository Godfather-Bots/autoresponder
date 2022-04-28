package dev.struchkov.autoresponder.repository;

import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.entity.UnitPointer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Интегрфейс для работы с хранилищем сущности {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerRepository<U extends Unit<U>> {

    UnitPointer<U> save(@NotNull UnitPointer<U> unitPointer);

    /**
     * @param entityId Идентификатор пользователя
     * @return Объект с последним обработанным {@link Unit} для пользователя
     */
    Optional<UnitPointer<U>> findByEntityId(Long entityId);

    void removeByEntityId(Long entityId);

}
