package org.sadtech.vkbot.core.data.impl;

import com.google.gson.JsonObject;
import org.sadtech.vkbot.core.data.ResponsibleData;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ResponseDataVk implements ResponsibleData {

    private Queue<JsonObject> jsonObjects = new ConcurrentLinkedQueue<>();

    @Override
    public void add(JsonObject jsonObject) {
        jsonObjects.offer(jsonObject);
    }

    @Override
    public void remove(int id) {
        jsonObjects.remove(id);
    }

    @Override
    public void cleanAll() {
        jsonObjects.clear();
    }

    public Queue<JsonObject> getJsonObjects() {
        return jsonObjects;
    }
}
