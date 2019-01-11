package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.Unit;

import java.util.Collection;

public interface UnitRepository {

    Unit getUnitById(Integer idUnit);

    void addUnit(Unit unit);

    void addUnits(Collection<Unit> units);

    void removeUnit(Integer idUnit);
}
