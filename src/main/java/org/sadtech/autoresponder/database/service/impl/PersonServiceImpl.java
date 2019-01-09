package org.sadtech.autoresponder.database.service.impl;

import org.sadtech.autoresponder.database.repository.PersonRepository;
import org.sadtech.autoresponder.database.entity.Person;
import org.sadtech.autoresponder.database.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repositoriy;

    @Override
    public void addUser(Person user) {
        repositoriy.saveAndFlush(user);
    }

    @Override
    public void removeUser(Long id) {
        repositoriy.deleteById(id);
    }

    @Override
    public Person getUserByID(Long id) {
        return repositoriy.getOne(id);
    }

    @Override
    public Map<String, Integer> getSocialNetwork(Long id) {
        return repositoriy.getOne(id).getSocialNetworks();
    }

    @Override
    public Person getUserBySocialNetworksId(String type, Integer socialNetworksId) {
        return repositoriy.getUserBySocialNetworksId(type, socialNetworksId);
    }
}
