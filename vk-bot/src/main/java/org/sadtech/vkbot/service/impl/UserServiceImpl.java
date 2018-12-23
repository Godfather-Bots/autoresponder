package org.sadtech.vkbot.service.impl;

import org.sadtech.vkbot.entity.User;
import org.sadtech.vkbot.repo.UserRepository;
import org.sadtech.vkbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        User savedUser = userRepository.saveAndFlush(user);
        return savedUser;
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }
}
