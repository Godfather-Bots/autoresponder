package org.sadtech.consultant.processing;

import org.sadtech.consultant.database.entity.Mail;
import org.sadtech.consultant.database.service.MailService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageLogicService {

    private MailService messageService;

    public MessageLogicService(MailService messageService) {
        this.messageService = messageService;
    }

    public void addMessage(Mail message) {
        messageService.addMessage(message);
    }

    public List<Mail> getMessageRange(long data) {
        return messageService.getMessageRange(data);
    }
}
