package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.entity.User;
import org.sadtech.consultant.repository.UserRepositoriy;
import org.sadtech.consultant.service.UserService;
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
