package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.entity.Messages;
import org.sadtech.consultant.database.repository.MessageRepository;
import org.sadtech.consultant.database.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;

    public void addMessage(Messages message) {
        repository.saveAndFlush(message);
    }

    @Override
    public List<Messages> getMessageRange(Long date) {
        return (List<Messages>) repository.getMessagesByRange(date);
    }
}
