package org.sadtech.autoresponder.service;

import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.repository.UnitPointerRepository;
import org.sadtech.autoresponder.repository.UnitPointerRepositoryMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitPointerServiceImpl implements UnitPointerService {

    private static final Logger log = LoggerFactory.getLogger(UnitPointerServiceImpl.class);

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
    public void edit(Integer personId, Unit unit) {
        if (check(personId)) {
            unitPointerRepository.edit(new UnitPointer(personId, unit));
        }
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
