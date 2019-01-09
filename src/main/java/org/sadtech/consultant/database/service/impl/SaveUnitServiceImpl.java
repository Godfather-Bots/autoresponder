package org.sadtech.consultant.database.service.impl;

import org.sadtech.consultant.database.repository.SaveUnitRepositoriy;
import org.sadtech.consultant.database.service.SaveUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveUnitServiceImpl implements SaveUnitService {

    @Autowired
    private SaveUnitRepositoriy repositoriy;

}
