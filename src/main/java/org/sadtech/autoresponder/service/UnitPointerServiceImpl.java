package org.sadtech.autoresponder.service;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.repository.UnitPointerRepository;
import org.sadtech.autoresponder.repository.UnitPointerRepositoryMap;

public class UnitPointerServiceImpl implements UnitPointerService {

    private static final Logger log = Logger.getLogger(UnitPointerServiceImpl.class);

    private UnitPointerRepository unitPointerRepository;

    public UnitPointerServiceImpl() {
        this.unitPointerRepository = new UnitPointerRepositoryMap();
    }

    public UnitPointerServiceImpl(UnitPointerRepository unitPointerRepository) {
        this.unitPointerRepository = unitPointerRepository;
    }

    @Override
    public UnitPointer getByEntityId(Integer entityId) {
        return unitPointerRepository.findByEntityId(entityId);
    }

    @Override
    public void add(UnitPointer unitPointer) {
        unitPointerRepository.add(unitPointer);
        log.info("Пользователь отправлен в репозиторий");
    }

    @Override
    public boolean check(Integer entityId) {
        return unitPointerRepository.findByEntityId(entityId) != null;
    }
}
