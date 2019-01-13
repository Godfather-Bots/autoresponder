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
    private HashSet<String> words = new HashSet<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private UnitRepositoryImpl unitRepository = new UnitRepositoryImpl();
    private PersonRepositoryImpl personRepository = new PersonRepositoryImpl();

    private UnitServiceImpl unitService = new UnitServiceImpl(unitRepository);
    private PersonServiceImpl personService = new PersonServiceImpl(personRepository);
    private Autoresponder autoresponder = new Autoresponder(unitService, personService);


    @Before
    public void before() {
        words.add("тест");

        unit.setIdUnit(1);
        unit.setMatchThreshold(100);

        units.add(unit2);

        unit.setNextUnits(units);

        unit2.setIdUnit(2);
        unit2.setAnswer("Ответ с {0} параметрами!");
        unit2.setPriority(100);
        unit2.setKeyWords(words);
        unit2.setMatchThreshold(100);

        person.setUnit(unit);
        person.setId(1);

        unitRepository.addUnit(unit);
        unitRepository.addUnit(unit2);
        personRepository.addPerson(person);
    }

    @Test
    public void answer() {
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания"), "Ответ с {0} параметрами!");
    }

    @Test
    public void answer1() {
        ArrayList<String> words = new ArrayList<>();
        words.add("одним");
        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания", words), "Ответ с одним параметрами!");
    }

    @Test
    public void answer2() {
        String test = autoresponder.answer(person.getId(), "Привет это срабатывания");
        Assert.assertNull(test);
    }
}