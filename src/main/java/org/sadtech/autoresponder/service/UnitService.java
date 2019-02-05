package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;

import java.util.List;
import java.util.Set;

public interface UnitService {

    Set<Unit> menuUnit();

    void addUnit(Unit unit);

}
