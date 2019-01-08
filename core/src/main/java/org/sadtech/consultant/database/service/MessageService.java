package org.sadtech.consultant.database.service;

import org.sadtech.consultant.database.entity.Message;

import java.util.List;

public interface MessageService {

    void addMessage(Message message);

    List<Message> getMessageRange(Long date);
}
