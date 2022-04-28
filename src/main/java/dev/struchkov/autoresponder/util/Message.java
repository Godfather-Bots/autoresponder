package dev.struchkov.autoresponder.util;

/**
 * Класс содержащий сообщения для ошибок и логера.
 *
 * @author upagge [22.08.2019]
 */
public final class Message {

    static final String UTILITY_CLASS = "Клас утилита";
    public static final String UNIT_KEYWORDS = "Ключевые слова юнита: {} ({})";
    public static final String USER_MESSAGE_KEYWORDS = "Ключевые слова от пользователя: {}";
    public static final String INTERSECTION = "Пересечение: {} ({})";
    public static final String CROSSING_PERCENTAGE = "Процент: {} Необходимо: {}";

    private Message() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

}
