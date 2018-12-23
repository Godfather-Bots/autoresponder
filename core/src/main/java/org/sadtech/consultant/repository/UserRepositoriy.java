package org.sadtech.consultant.repository;

import org.sadtech.consultant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoriy extends JpaRepository<User, Long> {

}
