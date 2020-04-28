package org.sadtech.autoresponder.repository;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация хранилища для {@link UnitPointer} на основе Map.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointerRepositoryMap implements UnitPointerRepository {

    private Map<Long, UnitPointer> unitPointerMap = new HashMap<>();

    @Override
    public UnitPointer add(@NonNull UnitPointer unitPointer) {
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
        return unitPointer;
    }

    @Override
    public void edit(@NonNull UnitPointer unitPointer) {
        unitPointerMap.get(unitPointer.getEntityId()).setUnit(unitPointer.getUnit());
    }

    @Override
    public void remove(@NonNull Integer entityId) {
        unitPointerMap.remove(entityId);
    }

    @Override
    public void addAll(@NonNull Collection<UnitPointer> unitPointers) {
        unitPointers.parallelStream().forEach(unitPointer -> unitPointerMap.put(unitPointer.getEntityId(), unitPointer));
    }

    @Override
    public Optional<UnitPointer> findByEntityId(@NonNull Long entityId) {
        return Optional.ofNullable(unitPointerMap.get(entityId));
    }

}
