package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.UnitPointer;

public interface UnitPointerService {

    void add(UnitPointer unitPointer);

    boolean check(Integer entityId);

    UnitPointer getByEntityId(Integer entityId);

}
