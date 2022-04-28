package dev.struchkov.autoresponder.service;

import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.entity.UnitPointer;
import dev.struchkov.autoresponder.repository.UnitPointerRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static dev.struchkov.haiti.utils.Inspector.isNotNull;

public class UnitPointerServiceImpl<U extends Unit<U>> implements UnitPointerService<U> {

    private static final Logger log = LoggerFactory.getLogger(UnitPointerServiceImpl.class);

    private final UnitPointerRepository<U> unitPointerRepository;

    public UnitPointerServiceImpl(UnitPointerRepository<U> unitPointerRepository) {
        this.unitPointerRepository = unitPointerRepository;
    }

    @Override
    public Optional<UnitPointer<U>> getByEntityId(@NotNull Long entityId) {
        isNotNull(entityId);
        return unitPointerRepository.findByEntityId(entityId);
    }

    @Override
    public void removeByEntityId(@NotNull Long entityId) {
        isNotNull(entityId);
        unitPointerRepository.removeByEntityId(entityId);
    }

    @Override
    public void save(@NotNull UnitPointer<U> unitPointer) {
        isNotNull(unitPointer);
        unitPointerRepository.save(unitPointer);
        log.trace("Пользователь отправлен в репозиторий");
    }

}
