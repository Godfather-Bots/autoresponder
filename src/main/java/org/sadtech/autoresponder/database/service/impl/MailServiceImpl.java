package org.sadtech.autoresponder.database.service.impl;

import org.sadtech.autoresponder.database.entity.Mail;
import org.sadtech.autoresponder.database.repository.MailRepository;
import org.sadtech.autoresponder.database.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailRepository repository;

    public void addMessage(Mail message) {
        repository.saveAndFlush(message);
    }

    @Override
    public List<Mail> getMessageRange(Long date) {
        return (List<Mail>) repository.getMessagesByRange(date);
    }
}
