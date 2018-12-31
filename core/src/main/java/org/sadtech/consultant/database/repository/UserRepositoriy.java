package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoriy extends JpaRepository<User, Long> {

}
