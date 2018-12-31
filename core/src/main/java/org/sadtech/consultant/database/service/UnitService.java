package org.sadtech.consultant.database.service;

import org.sadtech.consultant.database.entity.Unit;

public interface UnitService {

    void addUnit(Unit unit);
    void removeUnit(Long id);

}
