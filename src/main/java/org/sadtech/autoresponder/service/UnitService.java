package org.sadtech.autoresponder.service;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Unit;

public interface UnitService {
    Unit nextUnit(Unit unit, String message);

    Unit getUnitById(Integer idUnit);
}
