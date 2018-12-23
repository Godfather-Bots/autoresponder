package org.sadtech.consultant.service;

import org.sadtech.consultant.entity.User;

public interface UserService {

    void addUser(User user);
    void removeUser(Long id);

}
