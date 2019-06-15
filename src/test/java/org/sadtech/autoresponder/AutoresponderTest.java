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

public class AutoresponderTest {

    private Autoresponder autoresponder;

    @Before
    public void setUp() {
        TestUnit dela = new TestUnit();
        dela.setKeyWord("дела", "делишки");
        dela.setMessage("хорошо");

        TestUnit delaTop = new TestUnit();
        delaTop.setPriority(100);
        delaTop.setKeyWord("делишки");
        delaTop.setMessage("отлично");

        TestUnit hello = new TestUnit();
        hello.setMessage("тест");
        hello.setKeyWord("привет");
        hello.setNextUnit(dela);
        hello.setNextUnit(delaTop);

        TestUnit number = new TestUnit();
        number.setKeyWord("89101234567");
        number.setMessage("ответ");

        TestUnit regExp = new TestUnit();
        regExp.setPattern(Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"));
        regExp.setMessage("регулярка");
        dela.setNextUnit(regExp);
        dela.setNextUnit(number);

        TestUnit unreal = new TestUnit();
        unreal.setKeyWord("витамин", "мультифрукт", "арбуз", "параметр");
        unreal.setMessage("победа");
        unreal.setMatchThreshold(100);

        Set<Unit> testUnits = new HashSet<>();
        testUnits.add(hello);
        testUnits.add(regExp);
        testUnits.add(unreal);

        UnitPointerServiceImpl unitPointerService = new UnitPointerServiceImpl(new UnitPointerRepositoryMap());
        autoresponder = new Autoresponder(unitPointerService, testUnits);
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
        TestUnit defaultUnit = new TestUnit();
        defaultUnit.setMessage("не знаю");
        autoresponder.setDefaultUnit(defaultUnit);

        Assert.assertEquals(((TestUnit) autoresponder.answer(2, "пока")).getMessage(), "не знаю");
    }

    @Test(expected = NotFoundUnitException.class)
    public void notDefaultAnswer() {
        autoresponder.answer(2, "пока");
    }

    @Test
    public void regExpAnswer() {
        String message = ((TestUnit) autoresponder.answer(1, "79101234567")).getMessage();
        Assert.assertEquals(message, "регулярка");
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
        TestUnit defaultUnit = new TestUnit();
        defaultUnit.setMessage("не знаю");
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