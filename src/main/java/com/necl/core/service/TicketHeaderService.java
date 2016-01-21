/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.service;

import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import java.util.List;

/**
 *
 * @author C13.207
 */
public interface TicketHeaderService {

    public TicketHeader findById(String id) throws Exception;

    public List<TicketHeader> findAll() throws Exception;

    public boolean save(TicketHeader ticketHeader) throws Exception;

    public boolean update(TicketHeader ticketHeader) throws Exception;

    public boolean delete(TicketHeader ticketHeader) throws Exception;

    public List<TicketHeader> storedShowApproves(User user) throws Exception;

    public List<TicketHeader> storedShowTicket(String ssoId) throws Exception;

    public List<TicketHeader> findByType(String type) throws Exception;

    public List<TicketHeader> findMonthYear(int month, int year, String division) throws Exception;
    
    public List<TicketHeader> findByUser(String user) throws Exception;
}
