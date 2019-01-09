package org.sadtech.consultant.processing;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Mail;
import org.sadtech.consultant.MessageSender;
import org.sadtech.consultant.database.entity.Person;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Component
public class MessageHandler {

    private MessageLogicService messageLogicService;
    private PersonLogicService userLogicService;
    private Map<String, MessageSender> senderHashMap = new HashMap<>();
    private long data;

    public MessageHandler(MessageLogicService messageLogicService, PersonLogicService userLogicService) {
        this.messageLogicService = messageLogicService;
        this.userLogicService = userLogicService;
        data = new Date().getTime() / 1000;
    }

    public void addMessageSendler(String type, MessageSender messageSender) {
        senderHashMap.put(type, messageSender);
    }

    @Async
    @Transactional
    public void processing() {
        log.info("process");
        while (true) {
            List<Mail> messages = messageLogicService.getMessageRange(data);
            if (messages.size()>0) {
                for (Mail message : messages) {
                    log.info(message);
                    Person person = message.getPerson();
                    Integer idNetSoc = person.getSocialNetworks().get(message.getSourceMessage());
                    senderHashMap.get(message.getSourceMessage()).send(idNetSoc, "Тестовое сообщение");

                    data = message.getDate();
                }
            }
        }
    }

}
