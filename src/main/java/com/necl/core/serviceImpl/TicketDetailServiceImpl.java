/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.serviceImpl;

import com.necl.core.dao.TicketDetailDao;
import com.necl.core.service.TicketDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C13.272
 */
@Service
@Transactional
public class TicketDetailServiceImpl implements TicketDetailService {

    @Autowired
    private TicketDetailDao ticketDetailDao;

    @Override
    public boolean delete(int id) throws Exception {
       return ticketDetailDao.delete(id);
    }

}
