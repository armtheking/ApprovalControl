package com.necl.training.daoImpl;

import com.necl.training.dao.TrainingPlanDao;
import com.necl.training.model.TrainingPlan;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingPlanDaoImpl implements TrainingPlanDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public TrainingPlan findByPlanID(Long planID) throws Exception {
        TrainingPlan trainingPlan = (TrainingPlan) sessionFactory.getCurrentSession().get(TrainingPlan.class, planID);
        return trainingPlan;
   }
    
}
