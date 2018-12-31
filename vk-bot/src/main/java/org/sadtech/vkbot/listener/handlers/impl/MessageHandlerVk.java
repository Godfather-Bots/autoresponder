package org.sadtech.vkbot.listener.handlers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.service.MessageService;
import org.sadtech.consultant.database.service.impl.MessageServiceImpl;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class MessageHandlerVk implements Observer {

    private Observable dispetcherHandler;
//    @Autowired
//    private MessageService service;

    public MessageHandlerVk(Observable dispetcherHandler) {
        this.dispetcherHandler = dispetcherHandler;
        dispetcherHandler.registerObserver(this);
    }

    @Override
    public void update(JsonObject object) {
        if (object.get("type").toString().equals("\"message_new\"")) {
            Gson gson = new Gson();
            Message message = gson.fromJson(object.getAsJsonObject("object"), Message.class);
            send(message);
        }
    }

    private void send(Message message) {
        log.info(message.getBody());
    }
}
