package org.sadtech.vkbot.listener;

import com.google.gson.JsonObject;

import java.util.List;

public interface Observer {
    void update(List<JsonObject> jsonObjects);
}
