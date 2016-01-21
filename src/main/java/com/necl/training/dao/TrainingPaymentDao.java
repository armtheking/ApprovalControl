package com.necl.training.dao;

import com.necl.training.model.TrainingPayment;

public interface TrainingPaymentDao {
     public TrainingPayment findByPaymentID(Long paymentID)throws Exception;
}
