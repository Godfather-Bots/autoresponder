package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.repository.SocialNetworksRepositoriy;
import org.sadtech.consultant.database.service.SocialNetworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworksServiceImpl implements SocialNetworksService {

    @Autowired
    private SocialNetworksRepositoriy repositoriy;

}
