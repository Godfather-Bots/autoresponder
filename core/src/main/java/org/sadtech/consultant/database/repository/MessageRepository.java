package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
