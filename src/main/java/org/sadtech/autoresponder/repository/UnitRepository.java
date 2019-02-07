package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.Unit;

import java.util.List;
import java.util.Set;

public interface UnitRepository {

    void addUnit(Unit unit);

    void addUnits(List<Unit> units);

    Set<Unit> menuUnits();

}
