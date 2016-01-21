package com.necl.training.serviceImpl;

import com.necl.training.dao.TrainingPaymentDao;
import com.necl.training.dao.TrainingPlanDao;
import com.necl.training.model.TrainingPayment;
import com.necl.training.service.TrainingPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingPaymentServiceImpl implements TrainingPaymentService {

    @Autowired
    private TrainingPaymentDao trainingPaymentDao;

    @Override
    public TrainingPayment findByPaymentID(Long paymentID) throws Exception {
        return trainingPaymentDao.findByPaymentID(paymentID);
    }

}
