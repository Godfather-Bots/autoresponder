package org.sadtech.autoresponder.service.impl;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.Parser;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private UnitRepository unitRepository;

    public Unit nextUnit(@NotNull Unit unit, @NotNull String message) {
        ArrayList<Unit> nextUnits = (ArrayList<Unit>) unit.getNextUnits();
        Unit unitReturn = new Unit();
        if (nextUnits.size() > 0) {
            Parser parser = new Parser();
            parser.setText(message);
            parser.parse();
            for (Unit nextUnit : nextUnits) {
                if (nextUnit.getKeyWords().retainAll(parser.getWords()) && (nextUnit.getPriority()>unitReturn.getPriority())) {
                    unitReturn = nextUnit;
                }
            }
            return unitReturn;
        } else {
            return null;
        }
    }

    @Override
    public Unit getUnitById(@NotNull Integer idUnit) {
        return unitRepository.getUnitById(idUnit);
    }


}

