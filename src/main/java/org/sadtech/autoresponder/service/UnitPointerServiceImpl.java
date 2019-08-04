package org.sadtech.autoresponder.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.repository.UnitPointerRepository;

@Slf4j
public class UnitPointerServiceImpl<U extends Unit> implements UnitPointerService<U> {

    private final UnitPointerRepository<U> unitPointerRepository;

    public UnitPointerServiceImpl(UnitPointerRepository<U> unitPointerRepository) {
        this.unitPointerRepository = unitPointerRepository;
    }

    @Override
    public UnitPointer<U> getByEntityId(@NonNull Integer entityId) {
        return unitPointerRepository.findByEntityId(entityId);
    }

    @Override
    public void edit(@NonNull Integer personId, U unit) {
        if (check(personId)) {
            unitPointerRepository.edit(new UnitPointer<>(personId, unit));
        }
    }

    @Override
    public void add(@NonNull UnitPointer<U> unitPointer) {
        unitPointerRepository.add(unitPointer);
        log.info("Пользователь отправлен в репозиторий");
    }

    @Override
    public boolean check(@NonNull Integer entityId) {
        return unitPointerRepository.findByEntityId(entityId) != null;
    }
}
