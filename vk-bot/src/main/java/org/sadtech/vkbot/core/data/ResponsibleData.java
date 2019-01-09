package org.sadtech.vkbot.core.data;

import com.google.gson.JsonObject;

import java.util.Queue;

public interface ResponsibleData {

    void add(JsonObject jsonObject);

    void remove(int id);

    void cleanAll();

    Queue<JsonObject> getJsonObjects();

}
