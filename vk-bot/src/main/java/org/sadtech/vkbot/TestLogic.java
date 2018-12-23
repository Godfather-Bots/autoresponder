package org.sadtech.vkbot;

import com.google.gson.JsonObject;

import java.util.List;

public class TestLogic implements Observer{

    private ResponseDataVk responseData;

    public TestLogic(ResponseDataVk responseData) {
        this.responseData = responseData;
        responseData.registerObserver(this);
    }

    @Override
    public void update(List<JsonObject> jsonObjects) {
        System.out.println(jsonObjects);
    }
}
