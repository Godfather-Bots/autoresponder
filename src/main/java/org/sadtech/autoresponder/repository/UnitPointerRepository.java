package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Map;

public interface UnitPointerRepository {

    Integer add(UnitPointer unitPointer);

    void edit(UnitPointer unitPointer);

    void remove(Integer entityId);

    void addAll(Map<Integer, UnitPointer> unitPointerMap);

    UnitPointer findByEntityId(Integer entityId);

}
