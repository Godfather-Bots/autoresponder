package org.sadtech.vkbot.service;

import org.sadtech.vkbot.entity.User;

public interface UserService {

    User addUser(User user);

    void removeUser(long id);

}
