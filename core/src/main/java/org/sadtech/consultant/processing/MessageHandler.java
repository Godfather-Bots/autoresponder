package org.sadtech.consultant.processing;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.database.entity.Messages;
import org.sadtech.consultant.database.service.MessageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Log4j
@Component
public class MessageHandler {

    private MessageService service;
    private long data;

    public MessageHandler(MessageService service) {
        this.service = service;
        data = new Date().getTime() / 1000;
    }

    @Async
    public void processing() {
        while (true) {
            List<Messages> messages = service.getMessageRange(data);
            for (Messages message : messages) {
                System.out.println(message);
                data = message.getDate();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
