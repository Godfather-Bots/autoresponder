package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {

    @Query("SELECT u FROM Messages u WHERE u.date > :date")
    Collection<Messages> getMessagesByRange(@Param("date") Long date);

}
