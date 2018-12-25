package org.sadtech.consultant.repository;

import org.sadtech.consultant.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
