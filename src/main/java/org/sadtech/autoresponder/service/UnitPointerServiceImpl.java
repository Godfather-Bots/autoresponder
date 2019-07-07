package org.sadtech.autoresponder.service;

import lombok.extern.slf4j.Slf4j;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.repository.UnitPointerRepository;
import org.sadtech.autoresponder.repository.UnitPointerRepositoryMap;

@Slf4j
public class UnitPointerServiceImpl implements UnitPointerService {

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
