package com.necl.core.service;

import com.necl.core.model.History;
import java.util.List;

public interface HistoryService {
    public List <History> findByTicketNo(String ticketNo) throws Exception;
    public void updateStatus(String ticketRev) throws Exception;
}
