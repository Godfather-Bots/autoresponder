package org.sadtech.consultant.database.service;

import org.sadtech.consultant.database.entity.Messages;

import java.util.List;

public interface MessageService {

    void addMessage(Messages message);

    List<Messages> getMessageRange(Long date);
}
