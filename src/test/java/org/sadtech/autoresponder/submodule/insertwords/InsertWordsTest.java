//package org.sadtech.autoresponder.submodule.insertwords;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Test;
//import org.sadtech.autoresponder.submodule.insertwords.InsertWords;
//
//import java.util.ArrayList;
//
//public class InsertWordsTest {
//
//    private ArrayList<String> arrayList = new ArrayList<>();
//    private InsertWords insertWords = new InsertWords();
//
//    @After
//    public void setUp()  {
//
//    }
//
//    @Test
//    public void insert() {
//        insertWords.setInText("Проверка {0} теста");
//        arrayList.add("первого");
//        insertWords.insert(arrayList);
//        Assert.assertEquals(insertWords.getOutText(),"Проверка первого теста");
//    }
//
//    @Test
//    public void insert2() {
//        insertWords.setInText("Проверка {0} теста и {1} {теста}");
//        arrayList.add("первого");
//        arrayList.add("второго");
//        insertWords.insert(arrayList);
//        Assert.assertEquals(insertWords.getOutText(),"Проверка первого теста и второго {теста}");
//    }
//
//    @Test
//    public void insert3() {
//        insertWords.setInText("Проверка {1} теста и {0} {теста}");
//        arrayList.add("первого");
//        arrayList.add("второго");
//        insertWords.insert(arrayList);
//        Assert.assertEquals(insertWords.getOutText(),"Проверка второго теста и первого {теста}");
//    }
//}