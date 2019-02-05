package org.sadtech.autoresponder.service.impl;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;
import org.sadtech.autoresponder.service.UnitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnitServiceImpl implements UnitService {

    private static final Logger log = Logger.getLogger(UnitServiceImpl.class);

    private UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Set<Unit> menuUnit() {
        return unitRepository.menuUnits();
    }

    @Override
    public void addUnit(Unit unit) {
        unitRepository.addUnit(unit);
    }

}

