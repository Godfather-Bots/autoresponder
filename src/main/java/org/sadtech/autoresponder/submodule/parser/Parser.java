package org.sadtech.autoresponder.submodule.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    @Getter
    private Set<String> words;

    @Setter
    private String text;

    public void parse() {
        Pattern p = Pattern.compile("[а-яА-Я0-9]+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            words.add(m.group());
        }
    }

}
