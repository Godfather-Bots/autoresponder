package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MailRepository extends JpaRepository<Mail, Long> {

    @Query("SELECT u FROM Mail u WHERE u.date > :date")
    Collection<Mail> getMessagesByRange(@Param("date") Long date);

}
