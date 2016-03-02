package com.necl.core.serviceImpl;

import com.necl.core.dao.HistoryDao;
import com.necl.core.model.History;
import com.necl.core.service.HistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryDao historyDao;

    @Override
    public List<History> findByTicketNo(String ticketNo) throws Exception {
        return historyDao.findByTicketNo(ticketNo);
    }

    @Override
    public void updateStatus(String ticketRev) throws Exception {
       historyDao.updateStatus(ticketRev);
    }

}
