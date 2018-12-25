package org.sadtech.vkbot.listener.data;

import com.google.gson.JsonObject;

import java.util.List;

public interface ResponsibleData {

    void add(JsonObject jsonObject);

    void remove(int id);

    void cleanAll();

    List<JsonObject> getJsonObjects();

}
