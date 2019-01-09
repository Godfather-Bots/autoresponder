package org.sadtech.autoresponder.database.service.impl;

import org.sadtech.autoresponder.database.repository.UnitRepositoriy;
import org.sadtech.autoresponder.database.entity.Unit;
import org.sadtech.autoresponder.database.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepositoriy repositoriy;

    public void addUnit(Unit unit) {
        repositoriy.saveAndFlush(unit);
    }

    public void removeUnit(Long id) {

    }
}
