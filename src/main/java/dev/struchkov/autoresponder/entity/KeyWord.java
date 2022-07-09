package dev.struchkov.autoresponder.entity;

import dev.struchkov.autoresponder.exception.AutoresponderException;

/**
 * Ключевое слово для юнитов.
 */
public class KeyWord {

    private final Integer important;
    private final String word;

    private KeyWord(Integer important, String word) {
        if (important < 0 || important > 10) {
            throw new AutoresponderException("Вес слова должен быть значением от 0 до 100");
        }
        this.important = important;
        this.word = word;
    }

    public static KeyWord of(Integer weight, String word) {
        return new KeyWord(weight, word);
    }

    public static KeyWord of(String word) {
        return new KeyWord(1, word);
    }

    public Integer getImportant() {
        return important;
    }

    public String getWord() {
        return word;
    }

}
