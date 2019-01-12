package org.sadtech.autoresponder.service;

import lombok.NonNull;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;

import java.util.List;

public interface UnitService {
    Unit nextUnit(Unit unit, String message);

    Unit getUnitById(Integer idUnit);

}
