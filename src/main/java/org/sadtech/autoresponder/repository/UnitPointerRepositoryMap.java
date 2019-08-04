package org.sadtech.autoresponder.repository;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация хранилища для {@link UnitPointer} на основе Map.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointerRepositoryMap<U extends Unit> implements UnitPointerRepository<U> {

    private Map<Integer, UnitPointer<U>> unitPointerMap = new HashMap<>();

    @Override
    public void add(@NonNull UnitPointer<U> unitPointer) {
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
    }

    @Override
    public void edit(@NonNull UnitPointer<U> unitPointer) {
        unitPointerMap.get(unitPointer.getEntityId()).setUnit(unitPointer.getUnit());
    }

    @Override
    public void remove(@NonNull Integer entityId) {
        unitPointerMap.remove(entityId);
    }

    @Override
    public void addAll(@NonNull Collection<UnitPointer<U>> unitPointers) {
        unitPointers.parallelStream().forEach(unitPointer -> unitPointerMap.put(unitPointer.getEntityId(), unitPointer));
    }

    @Override
    public UnitPointer<U> findByEntityId(@NonNull Integer entityId) {
        return unitPointerMap.get(entityId);
    }
}
