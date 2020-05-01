package org.sadtech.autoresponder.service;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Optional;

/**
 * Сервис для взаимодействия с сущностью {@link UnitPointer}.
 *
 * @author upagge [07/07/2019]
 */
public interface UnitPointerService<U extends Unit> {

    void save(@NonNull UnitPointer<U> unitPointer);

    Optional<UnitPointer<U>> getByEntityId(@NonNull Long entityId);

    void removeByEntityId(@NonNull Long entityId);

}
