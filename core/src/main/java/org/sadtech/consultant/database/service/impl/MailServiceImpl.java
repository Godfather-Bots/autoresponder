package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.entity.Mail;
import org.sadtech.consultant.database.repository.MailRepository;
import org.sadtech.consultant.database.service.MailService;
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
