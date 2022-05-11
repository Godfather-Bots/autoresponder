package dev.struchkov.autoresponder.util;

import dev.struchkov.autoresponder.entity.Unit;

import java.util.Set;

import static dev.struchkov.haiti.utils.Exceptions.utilityClass;
import static dev.struchkov.haiti.utils.Inspector.isNotEmpty;
import static dev.struchkov.haiti.utils.Inspector.isNotNull;

/**
 * Утилитарный класс с полезными методами для работы с юнитами.
 */
public class Units {

    private Units() {
        utilityClass();
    }

    /**
     * Позволяет связать два разных юнита. Используется, чтобы побороть циклические зависимости в различных фреймворках.
     *
     * @param first  К этому юниту будет присоединен юнит second.
     * @param second Этот юнит присоединяется после first.
     */
    public static void link(Unit first, Unit second) {
        isNotNull(first, second);
        final Set<Unit> nextUnits = first.getNextUnits();
        if (nextUnits != null) {
            nextUnits.add(second);
        }
    }

    public static void link(Unit first, Unit... other) {
        isNotNull(first);
        isNotEmpty(other);
    }

}
