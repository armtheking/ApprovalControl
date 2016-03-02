package com.necl.core.daoImpl;

import com.necl.core.dao.HistoryDao;
import com.necl.core.model.History;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryDaoImpl implements HistoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<History> findByTicketNo(String ticketNo) throws Exception {
        String sql = "SELECT * FROM tblHistory WHERE TicketNo = '" + ticketNo + "' ORDER BY Date ASC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(History.class);
        List results = query.list();
        return results;
    }

    @Override
    public void updateStatus(String ticketRev) throws Exception {
        String sql_update = "UPDATE tblHistory\n"
                + "SET Status = 'False' WHERE TicketRev = :ticketRev";
        SQLQuery query_update = sessionFactory.getCurrentSession().createSQLQuery(sql_update);
        query_update.setParameter("ticketRev", ticketRev);
        query_update.executeUpdate();
    }

}
