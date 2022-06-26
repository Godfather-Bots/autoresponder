//package dev.struchkov.autoresponder;
//
//import dev.struchkov.autoresponder.repository.UnitPointerRepositoryMap;
//import dev.struchkov.autoresponder.service.UnitPointerServiceImpl;
//import dev.struchkov.autoresponder.test.TestUnit;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class AutoResponderTest {
//
//    private AutoResponder<TestUnit> autoresponder;
//
//    @BeforeEach
//    public void setUp() {
//        final TestUnit dela = TestUnit.builder()
//                .keyWord("дела")
//                .keyWord("делишки")
//                .message("хорошо")
//                .build();
//
//        final TestUnit delaTop = TestUnit.builder()
//                .priority(100)
//                .keyWord("делишки")
//                .message("отлично")
//                .build();
//
//        final TestUnit hello = TestUnit.builder()
//                .keyWord("привет")
//                .message("тест")
//                .nextUnit(dela)
//                .nextUnit(delaTop)
//                .build();
//
//
//        final TestUnit number = TestUnit.builder()
//                .keyWord("89101234567")
//                .message("ответ")
//                .build();
//
//        final TestUnit regExp = TestUnit.builder()
//                .pattern(Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"))
//                .message("регулярка")
//                .build();
//        dela.setNextUnits(Stream.of(regExp, number).collect(Collectors.toSet()));
//
//        final Set<String> strings = Stream.of("витамин", "мультифрукт", "арбуз", "параметр").collect(Collectors.toSet());
//
//        final TestUnit unreal = TestUnit.builder()
//                .keyWords(strings)
//                .message("победа")
//                .matchThreshold(100)
//                .build();
//
//        final Set<TestUnit> testUnits = new HashSet<>();
//        testUnits.add(hello);
//        testUnits.add(regExp);
//        testUnits.add(unreal);
//
//        final UnitPointerRepositoryMap<TestUnit> unitPointerRepository = new UnitPointerRepositoryMap<>();
//        final UnitPointerServiceImpl<TestUnit> unitPointerService = new UnitPointerServiceImpl<>(unitPointerRepository);
//        autoresponder = new AutoResponder<>(unitPointerService, testUnits);
//    }
//
//    @Test
//    void simpleAnswer() {
//        final String message = autoresponder.answer(1L, "привет это тестирвоание функциональности").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("тест", message);
//        final String message1 = autoresponder.answer(2L, "привет, еще одно тестирование").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("тест", message1);
//    }
//
//    @Test
//    void defaultAnswer() {
//        final TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
//        autoresponder.setDefaultUnit(defaultUnit);
//
//        assertEquals("не знаю", autoresponder.answer(2L, "пока").orElseThrow(NullPointerException::new).getMessage());
//    }
//
//    @Test
//    void notDefaultAnswer() {
//        assertEquals(Optional.empty(), autoresponder.answer(2L, "пока"));
//    }
//
//    @Test
//    void regExpAnswer() {
//        final String message = autoresponder.answer(1L, "79101234567").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("регулярка", message);
//    }
//
//    @Test
//    void priorityAnswer() {
//        autoresponder.answer(1L, "привет");
//        autoresponder.answer(2L, "привет");
//        final String message = autoresponder.answer(1L, "как твои делишки").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("отлично", message);
//        final String message1 = autoresponder.answer(2L, "твои дела все еще хорошо?").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("хорошо", message1);
//    }
//
//    @Test
//    void showRegExpVsKeyWords() {
//        autoresponder.answer(1L, "привет");
//        autoresponder.answer(1L, "дела");
//        assertEquals("регулярка", autoresponder.answer(1L, "89101234567").orElseThrow(NullPointerException::new).getMessage());
//    }
//
//    @Test
//    void matchThreshold() {
//        autoresponder.answer(1L, "витамин я сьем, и арбуз получу");
//        final String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
//        final String answer = autoresponder.answer(1L, message).orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("победа", answer);
//    }
//
//    @Test
//    void generalAnswer() {
//        TestUnit defaultUnit = TestUnit.builder().message("не знаю").build();
//        autoresponder.setDefaultUnit(defaultUnit);
//        final String answer = autoresponder.answer(1L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("тест", answer);
//        assertEquals("хорошо", autoresponder.answer(1L, "как твои дела").orElseThrow(NullPointerException::new).getMessage());
//        final String answer2 = autoresponder.answer(2L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("тест", answer2);
//        assertEquals("не знаю", autoresponder.answer(1L, "нет").orElseThrow(NullPointerException::new).getMessage());
//        final String message = "параметр себе задам: покушать витамин и арбуз, а вместе все это мультифрукт";
//        assertEquals("победа", autoresponder.answer(3L, message).orElseThrow(NullPointerException::new).getMessage());
//        assertEquals("регулярка", autoresponder.answer(1L, "8912345678").orElseThrow(NullPointerException::new).getMessage());
//        final String answer3 = autoresponder.answer(1L, "привет это тестирование функциональности").orElseThrow(NullPointerException::new).getMessage();
//        assertEquals("тест", answer3);
//    }
//
//
//}