package org.sadtech.autoresponder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.impl.PersonRepositoryImpl;
import org.sadtech.autoresponder.repository.impl.UnitRepositoryImpl;
import org.sadtech.autoresponder.service.impl.PersonServiceImpl;
import org.sadtech.autoresponder.service.impl.UnitServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AutoresponderTest {

    private Person person = new Person();
    private Unit unit = new Unit();
    private Unit unit2 = new Unit();
    private Unit unit3 = new Unit();
    private ArrayList<Unit> units = new ArrayList<>();
    private UnitRepositoryImpl unitRepository = new UnitRepositoryImpl();
    private PersonRepositoryImpl personRepository = new PersonRepositoryImpl();

    private UnitServiceImpl unitService = new UnitServiceImpl(unitRepository);
    private PersonServiceImpl personService = new PersonServiceImpl(personRepository);
    private Autoresponder autoresponder = new Autoresponder(unitService, personService);


    @Before
    public void before() {
        HashSet<String> words = new HashSet<>();
        HashSet<String> words2 = new HashSet<>();
        words.add("тест");
        words2.add("тест");
        words2.add("привет");

        unit.setIdUnit(1);
        unit.setLevel(true);
        unit.setPriority(50);
        unit.setKeyWords(words);
        unit.setAnswer("Здравствуйте, вы написали в нашу компанию!");
        unit.setMatchThreshold(100);

        units.add(unit2);
        units.add(unit3);

        unit.setNextUnits(units);

        unit2.setIdUnit(2);
        unit2.setAnswer("Ответ с {0} параметрами!");
        unit2.setPriority(60);
        unit2.setKeyWords(words);
        unit2.setMatchThreshold(100);

        unit3.setIdUnit(3);
        unit3.setAnswer("Второй Ответ с {0} параметрами!");
        unit3.setPriority(50);
        unit3.setKeyWords(words2);
        unit3.setMatchThreshold(100);

        person.setUnit(unit);
        person.setId(1);

        unitRepository.addUnit(unit);
        unitRepository.addUnit(unit2);
        personRepository.addPerson(person);
    }

    @Test
    public void usualAnswer() {
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания"), "Ответ с {0} параметрами!");
    }

    @Test
    public void answerOneParameter() {
        ArrayList<String> words = new ArrayList<>();
        words.add("одним");
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания", words), "Ответ с одним параметрами!");
    }

    @Test
    public void NoAnswer() {
        String test = autoresponder.answer(person.getId(), "Привет это срабатывания");
        Assert.assertNull(test);
    }

    @Test
    public void answerTwoParameter() {
        ArrayList<String> words = new ArrayList<>();
        words.add("одним");
        words.add("двумя");
        unit2.setAnswer("Ответ с {0} и {1}");
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания", words), "Ответ с одним и двумя");
    }

    @Test
    public void incorrectSettingsWords() {
        ArrayList<String> words = new ArrayList<>();
        words.add("одним");
        words.add("двумя");
        unit2.setAnswer("Ответ с {1} и {3}");
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания", words), "Ответ с двумя и {3}");
    }

    @Test
    public void answerRepeatSingleParameter() {
        ArrayList<String> words = new ArrayList<>();
        words.add("одним");
        words.add("двумя");
        unit2.setAnswer("Ответ с {1} и {1}");
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания", words), "Ответ с двумя и двумя");
    }

    @Test
    public void answerByPriority() {
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания"), "Ответ с {0} параметрами!");
    }

    @Test
    public void answerNoPerson() {
        Assert.assertEquals(autoresponder.answer(100, "Привет это тест срабатывания"), "Здравствуйте, вы написали в нашу компанию!");
        Assert.assertEquals(autoresponder.answer(100, "Привет это тест срабатывания"), "Ответ с {0} параметрами!");

    }
}