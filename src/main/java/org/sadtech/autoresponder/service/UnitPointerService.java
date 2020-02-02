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
public interface UnitPointerService {

    void add(@NonNull UnitPointer unitPointer);

    /**
     * Проверка наличия {@link UnitPointer} для пользователя
     *
     * @param entityId Идентификатор пользователя
     * @return true - если найдено
     */
    boolean check(@NonNull Long entityId);

    Optional<UnitPointer> getByEntityId(@NonNull Long entityId);

    void edit(@NonNull Long entityId, Unit unit);


}
