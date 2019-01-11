package org.sadtech.autoresponder;

import lombok.AllArgsConstructor;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.service.UnitService;

@AllArgsConstructor
public class Autoresponder {

    private UnitService unitService;

    public Unit nextUnit(Integer idUnit, String message) {
        Unit unit = unitService.getUnitById(idUnit);
        return unitService.nextUnit(unit, message);
    }

}
