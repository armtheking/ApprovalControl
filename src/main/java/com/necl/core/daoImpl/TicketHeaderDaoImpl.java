/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.daoImpl;

import com.necl.core.model.TicketHeader;
import com.necl.core.dao.TicketHeaderDao;
import com.necl.core.model.User;

import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class TicketHeaderDaoImpl implements TicketHeaderDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TicketHeader findById(String id) throws Exception {
        TicketHeader ticketHeader = (TicketHeader) sessionFactory.getCurrentSession().get(TicketHeader.class, id);
        return ticketHeader;
    }

    @Override
    public List<TicketHeader> findAll() throws Exception {
        @SuppressWarnings("unchecked")
        List<TicketHeader> listTicketHeader = sessionFactory.getCurrentSession()
                .createCriteria(TicketHeader.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return listTicketHeader;
    }

    @Override
    public boolean save(TicketHeader ticketHeader) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(ticketHeader);
        return true;
    }

    @Override
    public boolean update(TicketHeader ticketHeader) throws Exception {
        sessionFactory.getCurrentSession().update(ticketHeader);
        return true;
    }

    @Override
    public boolean delete(TicketHeader ticketHeader) throws Exception {

        ticketHeader.setTicketFinished("D");
        sessionFactory.getCurrentSession().update(ticketHeader);

        return true;
    }

    @Override
    public List<TicketHeader> storedShowApproves(User user) throws Exception {

        String sql = "EXEC PRO_ShowApproves :positionCode , :divisionCode , :userName";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("positionCode", user.getPositionCode());
        query.setParameter("divisionCode", user.getDivisionCode());
        query.setParameter("userName", user.getSsoId());
        query.addEntity(TicketHeader.class);
        List results = query.list();
        return results;
    }

    @Override
    public List<TicketHeader> storedShowTicket(String ssoId) throws Exception {
        String sql = "EXEC PRO_ShowTickets :ssoId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("ssoId", ssoId);
        query.addEntity(TicketHeader.class);
        List results = query.list();
        return results;
    }

    @Override
    public List<TicketHeader> findByType(String type) throws Exception {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(TicketHeader.class);
        cr.add(Restrictions.eq("ticketType", type));
        cr.add(Restrictions.eq("ticketFinished", "P"));
        List results = cr.list();
        return results;
    }

    @Override
    public List<TicketHeader> findMonthYear(int month, int year, String division) throws Exception {

        String sql = "SELECT * FROM tblTicketsH "
                + "WHERE (YEAR(ApplicationDate) = :paramYear) AND (MONTH(ApplicationDate) = :paramMonth) AND (DivisionCode = :division)";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketHeader.class);
        query.setParameter("paramYear", year);
        query.setParameter("paramMonth", month);
        query.setParameter("division", division);
        List results = query.list();
        return results;
    }

    @Override
    public List<TicketHeader> findByUser(String user) throws Exception {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(TicketHeader.class);
        cr.add(Restrictions.eq("applicationName", user));
        cr.add(Restrictions.eq("ticketFinished", "P"));
        List results = cr.list();
        return results;
    }

}
