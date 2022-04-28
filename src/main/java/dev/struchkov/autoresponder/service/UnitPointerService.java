package dev.struchkov.autoresponder.service;

import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.entity.UnitPointer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Сервис для взаимодействия с сущностью {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerService<U extends Unit<U>> {

    void save(@NotNull UnitPointer<U> unitPointer);

    Optional<UnitPointer<U>> getByEntityId(@NotNull Long entityId);

    void removeByEntityId(@NotNull Long entityId);

}
