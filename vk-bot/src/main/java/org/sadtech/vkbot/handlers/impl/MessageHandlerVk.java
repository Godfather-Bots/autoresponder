package org.sadtech.vkbot.handlers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Person;
import org.sadtech.consultant.processing.MessageLogicService;
import org.sadtech.consultant.processing.PersonLogicService;
import org.sadtech.consultant.database.entity.Message;
import org.sadtech.vkbot.SourceMessage;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class MessageHandlerVk implements Observer {

    private MessageLogicService messageLogicService;
    private PersonLogicService userLogicService;

    public MessageHandlerVk(Observable dispetcherHandler, PersonLogicService userLogicService, MessageLogicService messageLogicService) {
        this.userLogicService = userLogicService;
        this.messageLogicService = messageLogicService;
        dispetcherHandler.registerObserver(this);
    }

    @Override
    public void update(JsonObject object) {
        if (object.get("type").toString().equals("\"message_new\"")) {
            Gson gson = new Gson();
            com.vk.api.sdk.objects.messages.Message message = gson.fromJson(object.getAsJsonObject("object"), com.vk.api.sdk.objects.messages.Message.class);
            sendProcessing(message);
        }
    }

    private void sendProcessing(com.vk.api.sdk.objects.messages.Message userMessage) {
        log.info(userMessage.getBody());
        Message message = new Message();
//        Person user = userLogicService.getUserBySocialId(SourceMessage.VK.name(), Long.valueOf(userMessage.getUserId()));
        Person user;
        user = new Person();
        user.getSocialNetworks().put(SourceMessage.VK.name(), userMessage.getUserId());
        user.setName("Name");
        user.setLastName("LastName");
        user.setCity("City");
        userLogicService.addUser(user);
        log.info(user);
        message.setUser(user);
        message.setText(userMessage.getBody());
        message.setDate(Long.valueOf(userMessage.getDate()));
        message.setSourceMessage(SourceMessage.VK.name());
        log.info(message);
        messageLogicService.addMessage(message);
    }
}
