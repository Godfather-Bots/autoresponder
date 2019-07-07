package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация хранилища для {@link UnitPointer} на основе Map.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointerRepositoryMap implements UnitPointerRepository {

    private Map<Integer, UnitPointer> unitPointerMap = new HashMap<>();

    @Override
    public void add(UnitPointer unitPointer) {
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
    }

    @Override
    public void edit(UnitPointer unitPointer) {
        unitPointerMap.get(unitPointer.getEntityId()).setUnit(unitPointer.getUnit());
    }

    @Override
    public void remove(Integer entityId) {
        unitPointerMap.remove(entityId);
    }

    @Override
    public void addAll(Collection<UnitPointer> unitPointers) {
        unitPointers.parallelStream().forEach(unitPointer -> unitPointerMap.put(unitPointer.getEntityId(), unitPointer));
    }

    @Override
    public UnitPointer findByEntityId(Integer entityId) {
        return unitPointerMap.get(entityId);
    }
}
