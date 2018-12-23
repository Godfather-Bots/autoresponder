package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.entity.Unit;
import org.sadtech.consultant.repository.UnitRepositoriy;
import org.sadtech.consultant.service.UnitService;
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
