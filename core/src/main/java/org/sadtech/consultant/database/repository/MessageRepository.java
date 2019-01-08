package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT u FROM Message u WHERE u.date > :date")
    Collection<Message> getMessagesByRange(@Param("date") Long date);

}
