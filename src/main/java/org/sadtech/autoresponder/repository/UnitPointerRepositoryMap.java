package org.sadtech.autoresponder.repository;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация хранилища для {@link UnitPointer} на основе Map.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointerRepositoryMap<U extends Unit> implements UnitPointerRepository<U> {

    private Map<Long, UnitPointer<U>> unitPointerMap = new HashMap<>();

    @Override
    public UnitPointer<U> save(@NonNull UnitPointer<U> unitPointer) {
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
        return unitPointer;
    }

    @Override
    public Optional<UnitPointer<U>> findByEntityId(@NonNull Long entityId) {
        return Optional.ofNullable(unitPointerMap.get(entityId));
    }

    @Override
    public void removeByEntityId(@NonNull Long entityId) {
        unitPointerMap.remove(entityId);
    }

}
