package dev.struchkov.autoresponder.compare;

import dev.struchkov.autoresponder.entity.Unit;

import java.util.Comparator;

/**
 * Компоратор для сортировки {@link Unit} по приоритету.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPriorityComparator implements Comparator<Unit<?>> {

    @Override
    public int compare(Unit unit1, Unit unit2) {
        return Integer.compare(unit1.getPriority(), unit2.getPriority());
    }

}
