package org.sadtech.vkbot.repo;

import org.sadtech.vkbot.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
