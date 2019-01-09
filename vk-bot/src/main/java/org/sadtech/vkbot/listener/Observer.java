package org.sadtech.vkbot.listener;

import com.google.gson.JsonObject;

public interface Observer {
    void update(JsonObject object);
}
