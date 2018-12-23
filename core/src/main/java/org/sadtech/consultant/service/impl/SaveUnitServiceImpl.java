package org.sadtech.consultant.service.impl;

import org.sadtech.consultant.repository.SaveUnitRepositoriy;
import org.sadtech.consultant.service.SaveUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveUnitServiceImpl implements SaveUnitService {

    @Autowired
    private SaveUnitRepositoriy repositoriy;

}
