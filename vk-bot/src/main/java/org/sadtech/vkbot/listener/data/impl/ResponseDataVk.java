package org.sadtech.vkbot.listener.data.impl;

import com.google.gson.JsonObject;
import org.sadtech.vkbot.listener.data.ResponsibleData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseDataVk implements ResponsibleData {

    private List<JsonObject> jsonObjects = new ArrayList<JsonObject>();

    @Override
    public void add(JsonObject jsonObject) {
        jsonObjects.add(jsonObject);
    }

    @Override
    public void remove(int id) {
        jsonObjects.remove(id);
    }

    @Override
    public void cleanAll() {
        jsonObjects.clear();
    }

    public List<JsonObject> getJsonObjects() {
        return jsonObjects;
    }
}
