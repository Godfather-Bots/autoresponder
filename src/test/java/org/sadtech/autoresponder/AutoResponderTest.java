package org.sadtech.autoresponder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.exception.NotFoundUnitException;
import org.sadtech.autoresponder.repository.UnitPointerRepositoryMap;
import org.sadtech.autoresponder.service.UnitPointerServiceImpl;
import org.sadtech.autoresponder.test.TestUnit;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoResponderTest {

    private AutoResponder autoresponder;

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

        Set<Unit> testUnits = new HashSet<>();
        testUnits.add(hello);
        testUnits.add(regExp);
        testUnits.add(unreal);

        UnitPointerServiceImpl unitPointerService = new UnitPointerServiceImpl(new UnitPointerRepositoryMap());
        autoresponder = new AutoResponder(unitPointerService, testUnits);
    }

    @Test
    public void simpleAnswer() {
        String message = ((TestUnit) autoresponder.answer(1, "привет это тестирвоание функциональности")).getMessage();
        Assert.assertEquals("тест", message);
        String message1 = ((TestUnit) autoresponder.answer(2, "привет, еще одно тестирование")).getMessage();
        Assert.assertEquals("тест", message1);
    }

    @Test
    public void defaultAnswer() {
        TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
        autoresponder.setDefaultUnit(defaultUnit);

        Assert.assertEquals("не знаю", ((TestUnit) autoresponder.answer(2, "пока")).getMessage());
    }

    @Test(expected = NotFoundUnitException.class)
    public void notDefaultAnswer() {
        autoresponder.answer(2, "пока");
    }

    @Test
    public void regExpAnswer() {
        String message = ((TestUnit) autoresponder.answer(1, "79101234567")).getMessage();
        Assert.assertEquals("регулярка", message);
    }

    @Test
    public void priorityAnswer() {
        autoresponder.answer(1, "привет");
        autoresponder.answer(2, "привет");
        String message = ((TestUnit) autoresponder.answer(1, "как твои делишки")).getMessage();
        Assert.assertEquals("отлично", message);
        String message1 = ((TestUnit) autoresponder.answer(2, "твои дела все еще хорошо?")).getMessage();
        Assert.assertEquals("хорошо", message1);
    }

    @Test
    public void showRegExpVsKeyWords() {
        autoresponder.answer(1, "привет");
        autoresponder.answer(1, "дела");
        Assert.assertEquals("регулярка", ((TestUnit) autoresponder.answer(1, "89101234567")).getMessage());
    }

    @Test(expected = NotFoundUnitException.class)
    public void matchThreshold() {
        autoresponder.answer(1, "витамин я сьем, и арбуз получу");
        String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
        String answer = ((TestUnit) autoresponder.answer(1, message)).getMessage();
        Assert.assertEquals("победа", answer);
    }

    @Test
    public void generalAnswer() {
        TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
        autoresponder.setDefaultUnit(defaultUnit);
        String answer = ((TestUnit) autoresponder.answer(1, "привет это тестирование функциональности")).getMessage();
        Assert.assertEquals("тест", answer);
        Assert.assertEquals("хорошо", ((TestUnit) autoresponder.answer(1, "как твои дела")).getMessage());
        String answer2 = ((TestUnit) autoresponder.answer(2, "привет это тестирование функциональности")).getMessage();
        Assert.assertEquals("тест", answer2);
        Assert.assertEquals("не знаю", ((TestUnit) autoresponder.answer(1, "нет")).getMessage());
        String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
        Assert.assertEquals("победа", ((TestUnit) autoresponder.answer(3, message)).getMessage());
        Assert.assertEquals("регулярка", ((TestUnit) autoresponder.answer(1, "8912345678")).getMessage());
        String answer3 = ((TestUnit) autoresponder.answer(1, "привет это тестирование функциональности")).getMessage();
        Assert.assertEquals("тест", answer3);
    }


}