package com.necl.training.daoImpl;

import com.necl.training.dao.TrainingPaymentDao;
import com.necl.training.model.TrainingPayment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingPaymentDaoImpl implements TrainingPaymentDao{

    @Autowired
     private SessionFactory sessionFactory;
    
    @Override
    public TrainingPayment findByPaymentID(Long paymentID) throws Exception {
        TrainingPayment trainingPayment = (TrainingPayment) sessionFactory.getCurrentSession().get(TrainingPayment.class, paymentID);
        return trainingPayment;
  }
    
}
