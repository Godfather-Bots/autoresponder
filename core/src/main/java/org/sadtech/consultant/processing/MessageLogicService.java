package org.sadtech.consultant.processing;

import org.sadtech.consultant.database.entity.Message;
import org.sadtech.consultant.database.service.MessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageLogicService {

    private MessageService messageService;

    public MessageLogicService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void addMessage(Message message) {
        messageService.addMessage(message);
    }

    public List<Message> getMessageRange(long data) {
        return messageService.getMessageRange(data);
    }
}
