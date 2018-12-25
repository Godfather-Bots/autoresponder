package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.entity.Message;
import org.sadtech.consultant.repository.MessageRepository;
import org.sadtech.consultant.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;

    public void addMessage(Message message) {
        repository.saveAndFlush(message);
    }
}
