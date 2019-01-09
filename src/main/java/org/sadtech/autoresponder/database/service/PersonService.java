package org.sadtech.autoresponder.database.service;

import org.sadtech.autoresponder.database.entity.Person;

import java.util.Map;

public interface PersonService {

    void addUser(Person user);

    void removeUser(Long id);

    Person getUserByID(Long id);

    Map<String, Integer> getSocialNetwork(Long id);

    Person getUserBySocialNetworksId(String type, Integer socialNetworksId);
}
