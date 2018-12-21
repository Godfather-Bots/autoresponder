package org.sadtech.vkbot.service;

import org.sadtech.vkbot.dao.User;

public interface UserService {

    User addUser(User user);

    void removeUser(long id);

}
