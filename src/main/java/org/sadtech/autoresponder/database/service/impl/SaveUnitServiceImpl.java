package org.sadtech.autoresponder.database.service.impl;

import org.sadtech.autoresponder.database.repository.SaveUnitRepositoriy;
import org.sadtech.autoresponder.database.service.SaveUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveUnitServiceImpl implements SaveUnitService {

    @Autowired
    private SaveUnitRepositoriy repositoriy;

}
