package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.repository.NextUnitRepository;
import org.sadtech.consultant.database.service.NextUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextUnitServiceImpl implements NextUnitService {

    @Autowired
    private NextUnitRepository repository;

}
