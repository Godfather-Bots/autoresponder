package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.entity.User;
import org.sadtech.consultant.database.repository.UserRepositoriy;
import org.sadtech.consultant.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoriy repositoriy;

    public void addUser(User user) {
        //@TODO: Написть добавление профиля в бд
    }

    public void removeUser(Long id) {
        //@TODO: Написать удаление профиля из бд
    }
}
