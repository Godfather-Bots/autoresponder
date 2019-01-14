package org.sadtech.autoresponder.repository.impl;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;

import java.util.*;

public class UnitRepositoryMap implements UnitRepository {

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
    public void removeUnit(Unit idUnit) {
        units.remove(idUnit.getIdUnit());
    }

    @Override
    public List<Unit> menuUnits() {
        ArrayList<Unit> unitsMenu = new ArrayList<>();
        for (Integer integer : units.keySet()) {
            if (units.get(integer).getLevel()) {
                unitsMenu.add(units.get(integer));
            }
        }
        return unitsMenu;
    }
}
