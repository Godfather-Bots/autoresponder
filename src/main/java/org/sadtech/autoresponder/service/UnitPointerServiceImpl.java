package org.sadtech.autoresponder.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.UnitPointer;
import org.sadtech.autoresponder.repository.UnitPointerRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UnitPointerServiceImpl<U extends Unit> implements UnitPointerService<U> {

    private final UnitPointerRepository<U> unitPointerRepository;

    @Override
    public Optional<UnitPointer<U>> getByEntityId(@NonNull Long entityId) {
        return unitPointerRepository.findByEntityId(entityId);
    }

    @Override
    public void removeByEntityId(@NonNull Long entityId) {
        unitPointerRepository.removeByEntityId(entityId);
    }

    @Override
    public void save(@NonNull UnitPointer<U> unitPointer) {
        unitPointerRepository.save(unitPointer);
        log.trace("Пользователь отправлен в репозиторий");
    }

}
