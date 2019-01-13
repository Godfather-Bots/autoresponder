package org.sadtech.autoresponder.submodule.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser = new Parser();
    private Set<String> words = new HashSet<>();

    @Test
    public void parse() {
        parser.setText("Проверка парсера на правильность");
        parser.parse();
        words.add("проверка");
        words.add("парсера");
        words.add("на");
        words.add("правильность");
        Assert.assertEquals(parser.getWords(), words);
    }
}