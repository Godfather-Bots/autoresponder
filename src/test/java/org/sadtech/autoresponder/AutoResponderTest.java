package org.sadtech.autoresponder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sadtech.autoresponder.repository.UnitPointerRepositoryMap;
import org.sadtech.autoresponder.service.UnitPointerServiceImpl;
import org.sadtech.autoresponder.test.TestUnit;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoResponderTest {

    private AutoResponder<TestUnit> autoresponder;

    @Before
    public void setUp() {
        TestUnit dela = TestUnit.builder()
                .keyWord("дела")
                .keyWord("делишки")
                .message("хорошо")
                .build();

        TestUnit delaTop = TestUnit.builder()
                .priority(100)
                .keyWord("делишки")
                .message("отлично")
                .build();

        TestUnit hello = TestUnit.builder()
                .keyWord("привет")
                .message("тест")
                .nextUnit(dela)
                .nextUnit(delaTop)
                .build();


        TestUnit number = TestUnit.builder()
                .keyWord("89101234567")
                .message("ответ")
                .build();

        TestUnit regExp = TestUnit.builder()
                .pattern(Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"))
                .message("регулярка")
                .build();
        dela.setNextUnits(Stream.of(regExp, number).collect(Collectors.toSet()));

        Set<String> strings = Stream.of("витамин", "мультифрукт", "арбуз", "параметр").collect(Collectors.toSet());

        TestUnit unreal = TestUnit.builder()
                .keyWords(strings)
                .message("победа")
                .matchThreshold(100)
                .build();

        Set<TestUnit> testUnits = new HashSet<>();
        testUnits.add(hello);
        testUnits.add(regExp);
        testUnits.add(unreal);

        UnitPointerServiceImpl unitPointerService = new UnitPointerServiceImpl(new UnitPointerRepositoryMap());
        autoresponder = new AutoResponder<>(unitPointerService, testUnits);
    }

    @Test
    public void simpleAnswer() {
        String message = autoresponder.answer(1L, "привет это тестирвоание функциональности").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("тест", message);
        String message1 = autoresponder.answer(2L, "привет, еще одно тестирование").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("тест", message1);
    }

    @Test
    public void defaultAnswer() {
        TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
        autoresponder.setDefaultUnit(defaultUnit);

        Assert.assertEquals("не знаю", autoresponder.answer(2L, "пока").orElseThrow(NullPointerException::new).getMessage());
    }

    @Test
    public void notDefaultAnswer() {
        Assert.assertEquals(Optional.empty(), autoresponder.answer(2L, "пока"));
    }

    @Test
    public void regExpAnswer() {
        String message = autoresponder.answer(1L, "79101234567").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("регулярка", message);
    }

    @Test
    public void priorityAnswer() {
        autoresponder.answer(1L, "привет");
        autoresponder.answer(2L, "привет");
        String message = autoresponder.answer(1L, "как твои делишки").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("отлично", message);
        String message1 = autoresponder.answer(2L, "твои дела все еще хорошо?").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("хорошо", message1);
    }

    @Test
    public void showRegExpVsKeyWords() {
        autoresponder.answer(1L, "привет");
        autoresponder.answer(1L, "дела");
        Assert.assertEquals("регулярка", autoresponder.answer(1L, "89101234567").orElseThrow(NullPointerException::new).getMessage());
    }

    @Test
    public void matchThreshold() {
        autoresponder.answer(1L, "витамин я сьем, и арбуз получу");
        String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
        String answer = autoresponder.answer(1L, message).orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("победа", answer);
    }

    @Test
    public void generalAnswer() {
        TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
        autoresponder.setDefaultUnit(defaultUnit);
        String answer = autoresponder.answer(1L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("тест", answer);
        Assert.assertEquals("хорошо", autoresponder.answer(1L, "как твои дела").orElseThrow(NullPointerException::new).getMessage());
        String answer2 = autoresponder.answer(2L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("тест", answer2);
        Assert.assertEquals("не знаю", autoresponder.answer(1L, "нет").orElseThrow(NullPointerException::new).getMessage());
        String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
        Assert.assertEquals("победа", autoresponder.answer(3L, message).orElseThrow(NullPointerException::new).getMessage());
        Assert.assertEquals("регулярка", autoresponder.answer(1L, "8912345678").orElseThrow(NullPointerException::new).getMessage());
        String answer3 = autoresponder.answer(1L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
        Assert.assertEquals("тест", answer3);
    }


}