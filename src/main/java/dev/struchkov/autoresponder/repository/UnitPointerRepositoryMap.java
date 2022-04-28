package dev.struchkov.autoresponder.repository;

import dev.struchkov.autoresponder.entity.Unit;
import dev.struchkov.autoresponder.entity.UnitPointer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static dev.struchkov.haiti.utils.Inspector.isNotNull;

/**
 * Реализация хранилища для {@link UnitPointer} на основе Map.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPointerRepositoryMap<U extends Unit<U>> implements UnitPointerRepository<U> {

    private final Map<Long, UnitPointer<U>> unitPointerMap = new HashMap<>();

    @Override
    public UnitPointer<U> save(@NotNull UnitPointer<U> unitPointer) {
//        isNotNull(unitPointer);
        unitPointerMap.put(unitPointer.getEntityId(), unitPointer);
        return unitPointer;
    }

    @Override
    public Optional<UnitPointer<U>> findByEntityId(Long entityId) {
        isNotNull(entityId);
        return Optional.ofNullable(unitPointerMap.get(entityId));
    }

    @Override
    public void removeByEntityId(Long entityId) {
        isNotNull(entityId);
        unitPointerMap.remove(entityId);
    }

}
