package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.repository.SocialNetworksRepositoriy;
import org.sadtech.consultant.service.SocialNetworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworksServiceImpl implements SocialNetworksService {

    @Autowired
    private SocialNetworksRepositoriy repositoriy;

}
