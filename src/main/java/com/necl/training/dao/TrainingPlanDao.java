package com.necl.training.dao;

import com.necl.training.model.TrainingPlan;

public interface TrainingPlanDao {
      public TrainingPlan findByPlanID(Long planID)throws Exception;
}
