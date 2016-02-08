/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.daoImpl;

import com.necl.core.dao.TicketDetailDao;
import com.necl.core.model.TicketDetail;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author C13.272
 */
@Repository
public class TicketDetailDaoImpl implements TicketDetailDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM tblTicketsD WHERE id = :id";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketDetail.class);
        query.setParameter("id", id);
        System.out.println("xx: "+id);
        query.executeUpdate();
        return true;
    }

}
