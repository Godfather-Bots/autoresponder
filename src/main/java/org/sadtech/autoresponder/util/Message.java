package org.sadtech.autoresponder.util;

/**
 * Класс содержащий сообщения для ошибок и логера.
 *
 * @author upagge [22.08.2019]
 */
final class Message {

    static final String UTILITY_CLASS = "Клас утилита";

    private Message() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
