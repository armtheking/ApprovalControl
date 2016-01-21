package com.necl.training.serviceImpl;

import com.necl.training.dao.TrainingPlanDao;
import com.necl.training.model.TrainingPlan;
import com.necl.training.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingPlanServiceImpl implements TrainingPlanService{

    @Autowired
    private TrainingPlanDao trainingPlanDao;
    
    @Override
    public TrainingPlan findByPlanID(Long planID) throws Exception {
        return trainingPlanDao.findByPlanID(planID);}
    
}
