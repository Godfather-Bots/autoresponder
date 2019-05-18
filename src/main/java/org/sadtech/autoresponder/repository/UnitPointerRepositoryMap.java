package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.HashMap;
import java.util.Map;

public class UnitPointerRepositoryMap implements UnitPointerRepository {

    private Map<Integer, UnitPointer> unitPointerMap = new HashMap<>();

    @Override
    public Integer add(UnitPointer unitPointer) {
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
        return unitPointer.getEntityId();
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
    public void addAll(Map<Integer, UnitPointer> unitPointerMap) {
        this.unitPointerMap.putAll(unitPointerMap);
    }

    @Override
    public UnitPointer findByEntityId(Integer entityId) {
        return unitPointerMap.get(entityId);
    }
}
