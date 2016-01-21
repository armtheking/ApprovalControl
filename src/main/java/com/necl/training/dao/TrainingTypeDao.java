package com.necl.training.dao;

import com.necl.training.model.TrainingType;


public interface TrainingTypeDao {
        public TrainingType findByTypeID(Long typeID)throws Exception;
    
}
