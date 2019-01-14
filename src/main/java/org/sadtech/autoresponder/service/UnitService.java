package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;

public interface UnitService {

    Unit nextUnit(Unit unit, String message);

    Unit getUnitById(Integer idUnit);

    Unit menuUnit();

}
