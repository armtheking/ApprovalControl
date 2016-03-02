/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.serviceImpl;

import com.necl.core.model.TicketHeader;
import com.necl.core.dao.TicketHeaderDao;
import com.necl.core.model.Item;
import com.necl.core.model.User;
import com.necl.core.service.ItemService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C13.207
 */
@Service
@Transactional
public class TicketHeaderServiceImpl implements TicketHeaderService {

    @Autowired
    private TicketHeaderDao ticketHeaderDao;

    @Autowired
    private UserService userService;

    @Autowired
    ItemService itemService;

    @Override
    public TicketHeader findById(String id) throws Exception {
        return ticketHeaderDao.findById(id);
    }

    @Override
    public List<TicketHeader> findAll() throws Exception {
        return ticketHeaderDao.findAll();
    }

    @Override
    public boolean save(TicketHeader ticketHeader) throws Exception {
        return ticketHeaderDao.save(setTicketH(ticketHeader));
    }

    @Override
    public boolean update(TicketHeader ticketHeader) throws Exception {
        return ticketHeaderDao.update(ticketHeader);
    }

    @Override
    public boolean delete(TicketHeader ticketHeader) throws Exception {
        return ticketHeaderDao.delete(ticketHeader);
    }

    @Override
    public List<TicketHeader> storedShowApproves(User user) throws Exception {
        return ticketHeaderDao.storedShowApproves(user);
    }

    private TicketHeader setTicketH(TicketHeader ticketHeader) throws Exception {
        User user = userService.findBySso(ticketHeader.getApplicationName());

        Item item = itemService.findItemTicket(ticketHeader.getReqTotalAmt(), ticketHeader.getTicketType());

        //ticketHeader.getTicketdetail().setTicketHeader(ticketHeader);
        //Set Detail User
        ticketHeader.setDivisionCode(user.getDivisionCode());
        ticketHeader.setSectionCode(user.getSectionCode());
        ticketHeader.setSubSectionCode(user.getSubSectionCode());
        ticketHeader.setApplicationID(String.valueOf(user.getId()));
        ticketHeader.setApplicationPosition(user.getPositionCode());
        //Set Detail Item
        ticketHeader.setItem(item.getItem());
        ticketHeader.setCategoryId(item.getCategoryId());
        ticketHeader.setSubCategoryId(item.getSubCategoryId());
        return ticketHeader;
    }

    @Override
    public List<TicketHeader> storedShowTicket(String ssoId) throws Exception {
        return ticketHeaderDao.storedShowTicket(ssoId);
    }

    @Override
    public List<TicketHeader> findByType(String type) throws Exception {
        return ticketHeaderDao.findByType(type);
    }

    @Override
    public List<TicketHeader> findByTypeArea(String type, String area) throws Exception {
        return ticketHeaderDao.findByTypeArea(type, area);
    }

    @Override
    public List<TicketHeader> findMonthYear(int month, int year, String division) throws Exception {
        return ticketHeaderDao.findMonthYear(month, year, division);
    }

    @Override
    public List<TicketHeader> findByUser(String user) throws Exception {
        return ticketHeaderDao.findByUser(user);
    }

    @Override
    public List<TicketHeader> findFinish() throws Exception {
        return ticketHeaderDao.findFinish();
    }

    @Override
    public List<TicketHeader> findFinish(String area) throws Exception {
        return ticketHeaderDao.findFinish(area);
    }

    @Override
    public List<TicketHeader> findMonthYearArea(int month, int year, String division, String area) throws Exception {
        return ticketHeaderDao.findMonthYearArea(month, year, division, area);
    }

}
