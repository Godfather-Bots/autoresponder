package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.entity.Message;
import org.sadtech.consultant.database.repository.MessageRepository;
import org.sadtech.consultant.database.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;

    public void addMessage(Message message) {
        repository.saveAndFlush(message);
    }
}
