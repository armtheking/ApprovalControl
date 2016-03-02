package com.necl.training.serviceImpl;

import com.necl.training.dao.TrainingParticipantDao;
import com.necl.training.service.TrainingParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingParticipantServiceImpl implements TrainingParticipantService {

    @Autowired
    private TrainingParticipantDao trainingParticipantDao;

    @Override
    public boolean delete(long id) throws Exception {
        return trainingParticipantDao.delete(id);
    }

}
