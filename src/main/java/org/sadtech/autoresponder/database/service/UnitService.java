package org.sadtech.autoresponder.database.service;

import org.sadtech.autoresponder.database.entity.Unit;

public interface UnitService {

    void addUnit(Unit unit);

    void removeUnit(Long id);

}
