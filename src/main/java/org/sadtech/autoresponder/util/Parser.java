package org.sadtech.autoresponder.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Разбивает строку на множество слов, удаляя предлоги.
 *
 * @author upagge [07/07/2019]
 */
public class Parser {

    @Description("Множество предлогов")
    private static final Set<String> pretexts = Stream
            .of("в", "без", "до", "из", "к", "на", "по", "о", "от", "перед", "при", "с", "у", "за", "над", "об",
                    "под", "про", "для")
            .collect(Collectors.toSet());

    private Parser() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Метод по разбиению строки на множество слов
     *
     * @param text Строка
     * @return Множество слов
     */
    public static Set<String> parse(String text) {
        String[] split = text.split("\\P{L}+");
        Set<String> words = Arrays.stream(split).map(String::toLowerCase).collect(Collectors.toSet());
        words.removeAll(pretexts);
        return words;
    }

}
