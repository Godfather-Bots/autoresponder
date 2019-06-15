package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.UnitPointer;

import java.util.Collection;

public interface UnitPointerRepository {

    void add(UnitPointer unitPointer);

    void edit(UnitPointer unitPointer);

    void remove(Integer entityId);

    void addAll(Collection<UnitPointer> unitPointerMap);

    UnitPointer findByEntityId(Integer entityId);

}
