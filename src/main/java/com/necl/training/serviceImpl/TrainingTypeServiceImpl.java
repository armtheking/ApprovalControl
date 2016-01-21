package com.necl.training.serviceImpl;

import com.necl.training.dao.TrainingTypeDao;
import com.necl.training.model.TrainingType;
import com.necl.training.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingTypeServiceImpl implements TrainingTypeService{

    @Autowired
    private TrainingTypeDao trainingTypeDao;

    @Override
    public TrainingType findByTypeID(Long typeID) throws Exception {
        return trainingTypeDao.findByTypeID(typeID);
    }

 
    
}
