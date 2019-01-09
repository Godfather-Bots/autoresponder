package org.sadtech.vkbot.core.handlers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Mail;
import org.sadtech.consultant.database.entity.Person;
import org.sadtech.consultant.processing.MessageLogicService;
import org.sadtech.consultant.processing.PersonLogicService;
import org.sadtech.vkbot.core.SourceMessage;
import org.sadtech.vkbot.core.VkApi;
import org.sadtech.vkbot.core.handlers.Handled;
import org.sadtech.vkbot.core.listener.Observer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class MessageHandlerVk implements Observer {

    private MessageLogicService messageLogicService;
    private PersonLogicService personLogicService;
    private Message userMessage;

    public MessageHandlerVk(@Qualifier("dispatcherHandler") Handled dispatcherHandler, PersonLogicService personLogicService, MessageLogicService messageLogicService) {
        this.messageLogicService = messageLogicService;
        this.personLogicService = personLogicService;
        dispatcherHandler.registerObserver(this);
    }

    @Override
    public void update(JsonObject object) {
        if (object.get("type").toString().equals("\"message_new\"")) {
            Gson gson = new Gson();
            userMessage = gson.fromJson(object.getAsJsonObject("object"), com.vk.api.sdk.objects.messages.Message.class);
            sortAndSend();
        }
    }

    public void sortAndSend() {
        Person user;
        Integer userVkId = userMessage.getUserId();
        if (personLogicService.checkPersonBySocialNetworksId(SourceMessage.VK.name(), userVkId)) {
            user = personLogicService.getUserBySocialId(SourceMessage.VK.name(), userMessage.getUserId());
        } else {
            user = new Person();
            UserXtrCounters userXtrCounters = VkApi.getUserVk(userVkId);
            user.setCity("city");
            user.setName(userXtrCounters.getFirstName());
            user.setLastName(userXtrCounters.getLastName());
            user.getSocialNetworks().put(String.valueOf(SourceMessage.VK), userMessage.getUserId());
            personLogicService.addUser(user);
        }

        Mail message = new Mail();
        message.setPerson(user);
        message.setText(userMessage.getBody());
        message.setDate(Long.valueOf(userMessage.getDate()));
        message.setSourceMessage(SourceMessage.VK.name());
        log.info(message);
        messageLogicService.addMessage(message);
    }
}
