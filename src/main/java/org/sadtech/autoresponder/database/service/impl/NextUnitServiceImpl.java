package org.sadtech.autoresponder.database.service.impl;

import org.sadtech.autoresponder.database.repository.NextUnitRepository;
import org.sadtech.autoresponder.database.service.NextUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextUnitServiceImpl implements NextUnitService {

    @Autowired
    private NextUnitRepository repository;

}
