package org.sadtech.autoresponder.service.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;

@Log4j
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private UnitRepository unitRepository;

    public Unit nextUnit(Unit unit, @NonNull String message) {
        ArrayList<Unit> nextUnits = (ArrayList<Unit>) unit.getNextUnits();
        if (nextUnits.size() > 0) {
            Parser parser = new Parser();
            parser.setText(message);
            parser.parse();
            Unit unitReturn = new Unit();
            unitReturn.setPriority(0);
            for (Unit nextUnit : nextUnits) {
                if (!Collections.disjoint(nextUnit.getKeyWords(), parser.getWords()) && (nextUnit.getPriority() > unitReturn.getPriority())) {
                    unitReturn = nextUnit;
                }
            }
            return unitReturn;
        } else {
            return null;
        }

    }

    @Override
    public Unit getUnitById(@NonNull Integer idUnit) {
        return unitRepository.getUnitById(idUnit);
    }

    @Override
    public Unit menuUnit() {
        Unit menuUnit = new Unit();
        menuUnit.setNextUnits(unitRepository.menuUnits());
        return menuUnit;
    }


}

