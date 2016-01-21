package com.necl.training.service;

import com.necl.training.model.TrainingType;


public interface TrainingTypeService {
    public TrainingType findByTypeID(Long typeID)throws Exception;

}
