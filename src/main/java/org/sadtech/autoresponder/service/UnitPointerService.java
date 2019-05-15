package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;

public interface UnitPointerService {

    void add(UnitPointer unitPointer);

    boolean check(Integer entityId);

    UnitPointer getByEntityId(Integer entityId);

    void edit(Integer personId, Unit unit);

}
