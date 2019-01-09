package org.sadtech.vkbot.core.sendler;

import org.sadtech.consultant.MessageSender;
import org.sadtech.consultant.processing.MessageHandler;
import org.sadtech.vkbot.core.SourceMessage;
import org.sadtech.vkbot.core.VkApi;
import org.springframework.stereotype.Component;

@Component
public class MessageSendVk implements MessageSender {

    public MessageSendVk(MessageHandler messageHandler) {
        messageHandler.addMessageSendler(SourceMessage.VK.name(), this);
    }

    @Override
    public void send(Integer idNetSoc, String text) {
        VkApi.sendMessage(idNetSoc, text);
    }
}
