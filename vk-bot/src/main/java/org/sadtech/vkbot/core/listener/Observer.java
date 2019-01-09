package org.sadtech.vkbot.core.listener;

import com.google.gson.JsonObject;

public interface Observer {
    void update(JsonObject object);
}
