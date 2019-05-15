package org.sadtech.autoresponder.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Возвращает Set слов из текста
 */
public class Parser {

    private Parser() {
        throw new IllegalStateException("Utility Class");
    }

    public static Set<String> parse(String text) {
        Pattern p = Pattern.compile("[а-яА-Я0-9]+");
        Matcher m = p.matcher(text);
        Set<String> words = new HashSet<>();
        while (m.find()) {
            words.add(m.group().toLowerCase());
        }
        return words;
    }

}
