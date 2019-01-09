package org.sadtech.consultant.database.service;

import org.sadtech.consultant.database.entity.Mail;

import java.util.List;

public interface MailService {

    void addMessage(Mail message);

    List<Mail> getMessageRange(Long date);
}
