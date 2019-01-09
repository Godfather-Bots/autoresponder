package org.sadtech.autoresponder.database.service;

import org.sadtech.autoresponder.database.entity.Mail;

import java.util.List;

public interface MailService {

    void addMessage(Mail message);

    List<Mail> getMessageRange(Long date);
}
