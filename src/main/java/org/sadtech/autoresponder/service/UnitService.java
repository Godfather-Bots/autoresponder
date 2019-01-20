package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;

import java.util.List;

public interface UnitService {

    List<Unit> menuUnit();

    void addUnitRepository(UnitRepository unitRepository);

}
