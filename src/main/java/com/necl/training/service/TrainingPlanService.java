package com.necl.training.service;

import com.necl.training.model.TrainingPlan;

public interface TrainingPlanService {
       public TrainingPlan findByPlanID(Long planID)throws Exception;
}
