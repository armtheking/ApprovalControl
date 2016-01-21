package com.necl.training.service;

import com.necl.training.model.TrainingPayment;

public interface TrainingPaymentService {
     public TrainingPayment findByPaymentID(Long paymentID)throws Exception;
}
