package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.repository.NextUnitRepositoriy;
import org.sadtech.consultant.service.NextUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextUnitServiceImpl implements NextUnitService {

    @Autowired
    private NextUnitRepositoriy repositoriy;

}
