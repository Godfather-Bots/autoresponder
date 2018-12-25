package org.sadtech.vkbot.listener.handlers;

import com.google.gson.JsonObject;
import org.sadtech.vkbot.listener.Observer;

import java.util.List;

public class MessageHandlerVk implements Observer {

    private DispetcherHandler dispetcherHandler;

    public MessageHandlerVk(DispetcherHandler dispetcherHandler) {
        this.dispetcherHandler = dispetcherHandler;
        dispetcherHandler.registerObserver(this);
    }

    @Override
    public void update(List<JsonObject> jsonObjects) {

    }
}
