package org.sadtech.vkbot;

import com.google.gson.JsonObject;

public interface ResponsibleData {

    void add(JsonObject jsonObject);
    void remove(JsonObject jsonObject);

}
