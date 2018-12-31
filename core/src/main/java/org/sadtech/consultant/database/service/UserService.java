package org.sadtech.consultant.database.service;

import org.sadtech.consultant.database.entity.User;

public interface UserService {

    void addUser(User user);
    void removeUser(Long id);

}
