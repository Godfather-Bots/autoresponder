//package org.sadtech.autoresponder;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.sadtech.autoresponder.entity.Person;
//import org.sadtech.autoresponder.entity.Unit;
//import org.sadtech.autoresponder.repository.UnitRepository;
//import org.sadtech.autoresponder.repository.impl.PersonRepositoryMap;
//import org.sadtech.autoresponder.service.impl.PersonServiceImpl;
//import org.sadtech.autoresponder.service.impl.UnitServiceImpl;
//
//import java.util.*;
//
//public class AutoresponderTest {
//
//    private Person person = new Person(1);
//    private TextUnit unit = new TextUnit();
//    private TextUnit unit2 = new TextUnit();
//    private TextUnit unit3 = new TextUnit();
//    private ArrayList<Unit> units = new ArrayList<>();
//    private TextUnitRepositoryList unitRepository = new TextUnitRepositoryList();
//    private PersonRepositoryMap personRepository = new PersonRepositoryMap();
//
//    private UnitServiceImpl unitService = new UnitServiceImpl();
//    private PersonServiceImpl personService = new PersonServiceImpl(personRepository);
//    private Autoresponder autoresponder = new Autoresponder(unitService, personService);
//
//
//    @Before
//    public void before() {
//        unitService.addUnitRepository(unitRepository);
//
//        HashSet<String> words = new HashSet<>();
//        HashSet<String> words2 = new HashSet<>();
//        words.add("тест");
//        words2.add("тест");
//        words2.add("привет");
//
//        unit.setPriority(50);
//        unit.setKeyWords(words);
//        unit.setAnswer("Здравствуйте, вы написали в нашу компанию!");
//        unit.setMatchThreshold(100);
//
//        units.add(unit2);
//        units.add(unit3);
//
//        unit.setNextUnits(units);
//
//        unit2.setAnswer("Ответ с {0} параметрами!");
//        unit2.setPriority(60);
//        unit2.setKeyWords(words);
//        unit2.setMatchThreshold(100);
//
//        unit3.setAnswer("Второй Ответ с {0} параметрами!");
//        unit3.setPriority(50);
//        unit3.setKeyWords(words2);
//        unit3.setMatchThreshold(100);
//
//        person.setUnit(unit);
//
//        unitRepository.addUnit(unit);
//        unitRepository.addUnit(unit2);
//        personRepository.addPerson(person);
//    }
//
//    @Test
//    @Ignore
//    public void usualAnswer() {
//        Unit unit = autoresponder.answer(person.getId(), "Привет это тест срабатывания");
//        Assert.assertEquals(((TextUnit) unit).getAnswer(), "Ответ с {0} параметрами!");
//    }
//
//
//    @Test
//    @Ignore
//    public void NoAnswer() {
//        person.setUnit(null);
//        autoresponder.answer(person.getId(), "Привет это срабатывания");
//    }
//
//
//    @Test
//    @Ignore
//    public void answerByPriority() {
//        Assert.assertEquals(autoresponder.answer(person.getId(), "Привет это тест срабатывания"), "Ответ с {0} параметрами!");
//    }
//
//    @Test
//    @Ignore
//    public void answerNoPerson() {
//        TextUnit textUnit = (TextUnit) autoresponder.answer(100, "Привет это тест срабатывания");
//        Assert.assertEquals(textUnit.getAnswer(), "Здравствуйте, вы написали в нашу компанию!");
//        textUnit = (TextUnit) autoresponder.answer(100, "Привет это тест срабатывания");
//        Assert.assertEquals(textUnit.getAnswer(), "Ответ с {0} параметрами!");
//    }
//
//
//    private class TextUnit extends Unit {
//        private String answer;
//
//        public TextUnit() {
//            super();
//        }
//
//        public String getAnswer() {
//            return answer;
//        }
//
//        public void setAnswer(String answer) {
//            this.answer = answer;
//        }
//    }
//
//    private class TextUnitRepositoryList implements UnitRepository {
//
//        List<TextUnit> textUnits = new ArrayList<>();
//
//
//        @Override
//        public void addUnit(TextUnit unit) {
//            textUnits.add(unit);
//        }
//
//        @Override
//        public void addUnits(List<TextUnit> units) {
//            textUnits.addAll(units);
//        }
//
//        @Override
//        public List<TextUnit> menuUnits() {
//            List<TextUnit> units = new ArrayList<>();
//            for (TextUnit textUnit : textUnits) {
//                units.add(textUnit);
//            }
//            return units;
//        }
//    }
//}