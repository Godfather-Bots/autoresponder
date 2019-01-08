package org.sadtech.consultant.processing;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Log4j
@Component
public class MessageHandler {

    private MessageLogicService messageLogicService;
    private PersonLogicService userLogicService;
    private long data;

    public MessageHandler(MessageLogicService messageLogicService, PersonLogicService userLogicService) {
        this.messageLogicService = messageLogicService;
        this.userLogicService = userLogicService;
        data = new Date().getTime() / 1000;
    }

    @Async
    public void processing() {
        while (true) {
            List<Message> messages = messageLogicService.getMessageRange(data);
            for (Message message : messages) {
                System.out.println(message);


                data = message.getDate();
            }
        }
    }

}
