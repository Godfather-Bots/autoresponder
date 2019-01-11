package org.sadtech.autoresponder.repository.impl;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UnitRepositoryImpl implements UnitRepository {

    private Map<Integer, Unit> units = new HashMap<>();

    @Override
    public Unit getUnitById(Integer idUnit) {
        return units.get(idUnit);
    }

    @Override
    public void addUnit(Unit unit) {
        units.put(unit.getIdUnit(), unit);
    }

    @Override
    public void addUnits(Collection<Unit> units) {
        units.addAll(units);
    }

    @Override
    public void removeUnit(Integer idUnit) {
        units.remove(idUnit);
    }
}
