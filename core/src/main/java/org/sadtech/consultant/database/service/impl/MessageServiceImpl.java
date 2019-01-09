package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.entity.Message;
import org.sadtech.consultant.database.repository.MessageRepository;
import org.sadtech.consultant.database.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;

    public void addMessage(Message message) {
        repository.saveAndFlush(message);
    }

    @Override
    public List<Message> getMessageRange(Long date) {
        return (List<Message>) repository.getMessagesByRange(date);
    }
}
