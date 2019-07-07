package org.sadtech.autoresponder.compare;

import org.sadtech.autoresponder.entity.Unit;

import java.util.Comparator;

/**
 * Компоратор для сортировки {@link Unit} по приоритету.
 *
 * @author upagge [07/07/2019]
 */
public class UnitPriorityComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {
        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        } else if (o1.getPriority().equals(o2.getPriority())) {
            return 0;
        }
        return 1;
    }

}
