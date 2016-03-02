package com.necl.training.daoImpl;

import com.necl.training.dao.TicketDTrainingDao;
import com.necl.training.model.TicketDTraining;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDTrainngDaoImpl implements TicketDTrainingDao{

     @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean delete(long id) throws Exception {
        String sql = "DELETE FROM tblTicketsDTraining WHERE id = :id";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketDTraining.class);
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }
    
}
