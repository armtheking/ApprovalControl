package com.necl.training.daoImpl;

import com.necl.training.dao.TrainingTypeDao;
import com.necl.training.model.TrainingType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TrainingType findByTypeID(Long typeID) throws Exception {
        TrainingType trainingType = (TrainingType) sessionFactory.getCurrentSession().get(TrainingType.class, typeID);
        return trainingType;
    }

}
