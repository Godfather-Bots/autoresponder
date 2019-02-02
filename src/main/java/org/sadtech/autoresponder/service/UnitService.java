package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;

import java.util.List;

public interface UnitService {

    List<Unit> menuUnit();

    void addUnit(Unit unit);

}
