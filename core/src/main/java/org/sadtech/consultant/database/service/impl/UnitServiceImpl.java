package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.repository.UnitRepositoriy;
import org.sadtech.consultant.database.entity.Unit;
import org.sadtech.consultant.database.service.UnitService;
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
