package org.sadtech.vkbot.handlers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Messages;
import org.sadtech.consultant.database.entity.SourceMessage;
import org.sadtech.consultant.database.service.MessageService;
import org.sadtech.vkbot.VkApi;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.springframework.stereotype.Component;

@Log4j
@Component

public class MessageHandlerVk implements Observer {

    private MessageService service;

    public MessageHandlerVk(Observable dispetcherHandler, MessageService messageService) {
        this.service = messageService;
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

    private void send(Message userMessage) {
        log.info(userMessage.getBody());
        Messages message = new Messages();
        message.setIdUser(Long.valueOf(userMessage.getUserId()));
        message.setText(userMessage.getBody());
        message.setDate(Long.valueOf(userMessage.getDate()));
        message.setSource(SourceMessage.VK);
        VkApi.sendMessage(userMessage.getUserId(), "Здравствуйте, " + VkApi.getUserName(userMessage.getUserId()) + "!\nВаше сообщение получено!\n");
        service.addMessage(message);
    }
}
