package com.necl.training.serviceImpl;

import com.necl.training.dao.TicketDTrainingDao;
import com.necl.training.service.TicketDTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketDTrainingServiceImpl implements TicketDTrainingService{

     @Autowired
    private TicketDTrainingDao ticketDTrainingDao;
    
    @Override
    public boolean delete(long id) throws Exception {
        return ticketDTrainingDao.delete(id);
    }
    
}
