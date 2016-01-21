/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.serviceImpl;

import com.necl.core.dao.ItemDao;
import com.necl.core.model.Item;
import com.necl.core.service.ItemService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C13.207
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{
    
    @Autowired
    ItemDao itemDao;

    @Override
    public Item findItemTicket(BigDecimal cost, String ticketType) {
        return itemDao.findItemTicket(cost, ticketType);
    }
    
}
