package org.sadtech.autoresponder.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Возвращает Set слов из текста
 */
public class Parser {

    private static final Set<String> pretexts = Stream
            .of("в", "без", "до", "из", "к", "на", "по", "о", "от", "перед", "при", "с", "у", "за", "над", "об",
                    "под", "про", "для")
            .collect(Collectors.toSet());

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
        words.removeAll(pretexts);
        return words;
    }

}
