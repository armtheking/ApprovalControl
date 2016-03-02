package com.necl.core.dao;

import com.necl.core.model.History;
import java.util.List;

public interface HistoryDao {
    public List <History> findByTicketNo(String ticketNo) throws Exception;
    public void updateStatus(String ticketRev) throws Exception;
    
}
