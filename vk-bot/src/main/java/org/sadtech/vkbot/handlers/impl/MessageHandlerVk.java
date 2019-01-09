package org.sadtech.vkbot.handlers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Message;
import org.sadtech.consultant.database.entity.Person;
import org.sadtech.consultant.processing.MessageLogicService;
import org.sadtech.consultant.processing.PersonLogicService;
import org.sadtech.vkbot.SourceMessage;
import org.sadtech.vkbot.VkApi;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class MessageHandlerVk implements Observer {

    private MessageLogicService messageLogicService;
    private PersonLogicService personLogicService;

    public MessageHandlerVk(Observable dispetcherHandler, PersonLogicService personLogicService, MessageLogicService messageLogicService) {
        this.messageLogicService = messageLogicService;
        this.personLogicService = personLogicService;
        dispetcherHandler.registerObserver(this);
    }

    @Override
    public void update(JsonObject object) {
        if (object.get("type").toString().equals("\"message_new\"")) {
            Gson gson = new Gson();
            log.info(object.getAsJsonObject("object"));
            com.vk.api.sdk.objects.messages.Message message = gson.fromJson(object.getAsJsonObject("object"), com.vk.api.sdk.objects.messages.Message.class);
            sendProcessing(message);
        }
    }

    private void sendProcessing(com.vk.api.sdk.objects.messages.Message userMessage) {
        Person user;
        Integer userVkId = userMessage.getUserId();
        log.info(VkApi.getUserVk(userVkId));
        if (personLogicService.checkPersonBySocialNetworksId(SourceMessage.VK.name(), userVkId)) {
            user = personLogicService.getUserBySocialId(SourceMessage.VK.name(), userMessage.getUserId());
        } else {
            user = new Person();
            UserXtrCounters userXtrCounters = VkApi.getUserVk(userVkId);
            user.setCity("ыыыы");
            user.setName(userXtrCounters.getFirstName());
            user.setLastName(userXtrCounters.getLastName());
            user.getSocialNetworks().put(String.valueOf(SourceMessage.VK), userMessage.getUserId());
            personLogicService.addUser(user);
        }

        Message message = new Message();
        message.setUser(user);
        message.setText(userMessage.getBody());
        message.setDate(Long.valueOf(userMessage.getDate()));
        message.setSourceMessage(SourceMessage.VK.name());
        log.info(message);
        messageLogicService.addMessage(message);
    }
}
