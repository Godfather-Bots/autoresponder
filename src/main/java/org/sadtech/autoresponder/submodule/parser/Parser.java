package org.sadtech.autoresponder.submodule.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Set<String> words = new HashSet<>();
    private String text;

    public Set<String> getWords() {
        return words;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void parse() {
        Pattern p = Pattern.compile("[а-яА-Я0-9]+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            words.add(m.group().toLowerCase());
        }
    }

}
