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
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.StringType;
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

        String sql = "SELECT * FROM tblTicketsH "
                + "WHERE (TicketType = :ticketType) AND (TicketsFinished = :ticketFinished)"
                + "ORDER BY ApplicationDate DESC ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketHeader.class);
        query.setParameter("ticketType", type);
        query.setParameter("ticketFinished", "P");
        List results = query.list();

        return results;
    }

    @Override
    public List<TicketHeader> findByTypeArea(String type, String area) throws Exception {

        String sql = "SELECT *\n"
                + "FROM  APP_USER INNER JOIN\n"
                + "tblMaster_Branch ON APP_USER.branchId = tblMaster_Branch.BranchID INNER JOIN\n"
                + "tblTicketsH ON APP_USER.SSO_ID = tblTicketsH.ApplicationName\n"
                + "WHERE     (tblTicketsH.TicketType = :ticketType) AND (tblTicketsH.TicketsFinished = :ticketFinished) AND  (tblMaster_Branch.Area = :area)\n"
                + "ORDER BY tblTicketsH.ApplicationDate DESC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketHeader.class);
        query.setParameter("ticketType", type);
        query.setParameter("ticketFinished", "P");
        query.setParameter("area", area);
        List results = query.list();

        return results;
    }

    @Override
    public List<TicketHeader> findMonthYear(int month, int year, String division) throws Exception {

        String sql = "SELECT  ticketNo, ticketType,  applicationDate, detailHeader, reqTotalAmt,applicationName, approvedName1, approvedStatus1, approvedName2, approvedStatus2 FROM tblTicketsH "
                + "WHERE (YEAR(ApplicationDate) = :paramYear) AND (MONTH(ApplicationDate) = :paramMonth) AND (DivisionCode = :division) AND (TicketsFinished != 'D')"
                + "ORDER BY ApplicationDate DESC ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("ticketNo", StringType.INSTANCE)
                .addScalar("ticketType", StringType.INSTANCE)
                .addScalar("applicationDate", CalendarType.INSTANCE)
                .addScalar("detailHeader", StringType.INSTANCE)
                .addScalar("reqTotalAmt", BigDecimalType.INSTANCE)
                .addScalar("applicationName", StringType.INSTANCE)
                .addScalar("approvedName1", StringType.INSTANCE)
                .addScalar("approvedStatus1", BooleanType.INSTANCE)
                .addScalar("approvedName2", StringType.INSTANCE)
                .addScalar("approvedStatus2", BooleanType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(TicketHeader.class));
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

    @Override
    public List<TicketHeader> findFinish() throws Exception {
        String sql = "SELECT ticketNo, ticketType,  applicationDate, detailHeader, applicationName, approvedName1, approvedName2, payment, paidBy FROM   tblTicketsH  WHERE (TicketsFinished = 'F')\n"
                + "ORDER BY ApplicationDate DESC ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("ticketNo", StringType.INSTANCE)
                .addScalar("ticketType", StringType.INSTANCE)
                .addScalar("applicationDate", CalendarType.INSTANCE)
                .addScalar("detailHeader", StringType.INSTANCE)
                .addScalar("applicationName", StringType.INSTANCE)
                .addScalar("approvedName1", StringType.INSTANCE)
                .addScalar("approvedName2", StringType.INSTANCE)
                .addScalar("payment", StringType.INSTANCE)
                .addScalar("paidBy", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(TicketHeader.class));

        List results = query.list();
        return results;
    }
//
//    String sql = "SELECT Header_Id as id, Year as year, Half as half, Description as description, StartAppraisal as startAppraisal, EndAppraisal as endAppraisal, Status as status FROM HeaderAppraisal h WHERE  (Status != :status)";
//    Query query = getSession().createSQLQuery(sql)
//            .setResultTransformer(Transformers.aliasToBean(HeaderAppraisal.class)).setParameter("status", status);
//    List results = query.list();

    @Override
    public List<TicketHeader> findFinish(String area) throws Exception {
        String sql = "SELECT ticketNo, ticketType,  applicationDate, detailHeader, applicationName, approvedName1, approvedName2, payment, paidBy \n"
                + "FROM APP_USER LEFT OUTER JOIN\n"
                + "tblMaster_Branch ON APP_USER.branchId = tblMaster_Branch.BranchID RIGHT OUTER JOIN\n"
                + " tblTicketsH ON APP_USER.SSO_ID = tblTicketsH.ApplicationName\n"
                + "WHERE (tblMaster_Branch.Area = '" + area + "') AND (tblTicketsH.TicketsFinished = 'F')\n"
                + "ORDER BY ApplicationDate DESC ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("ticketNo", StringType.INSTANCE)
                .addScalar("ticketType", StringType.INSTANCE)
                .addScalar("applicationDate", CalendarType.INSTANCE)
                .addScalar("detailHeader", StringType.INSTANCE)
                .addScalar("applicationName", StringType.INSTANCE)
                .addScalar("approvedName1", StringType.INSTANCE)
                .addScalar("approvedName2", StringType.INSTANCE)
                .addScalar("payment", StringType.INSTANCE)
                .addScalar("paidBy", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(TicketHeader.class));

        List results = query.list();

        return results;
    }

    @Override
    public List<TicketHeader> findMonthYearArea(int month, int year, String division, String area) throws Exception {
        String sql = "SELECT  ticketNo, ticketType,  applicationDate, detailHeader, reqTotalAmt,applicationName, approvedName1, approvedStatus1, approvedName2, approvedStatus2 \n"
                + "FROM APP_USER LEFT OUTER JOIN\n"
                + "tblMaster_Branch ON APP_USER.branchId = tblMaster_Branch.BranchID RIGHT OUTER JOIN\n"
                + "tblTicketsH ON APP_USER.SSO_ID = tblTicketsH.ApplicationName\n"
                + "WHERE (tblMaster_Branch.Area = '" + area + "') AND (YEAR(tblTicketsH.ApplicationDate) = :paramYear) AND (MONTH(tblTicketsH.ApplicationDate) = :paramMonth) AND (tblTicketsH.DivisionCode = :division) AND (tblTicketsH.TicketsFinished != 'D')\n"
                + "ORDER BY ApplicationDate DESC ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("ticketNo", StringType.INSTANCE)
                .addScalar("ticketType", StringType.INSTANCE)
                .addScalar("applicationDate", CalendarType.INSTANCE)
                .addScalar("detailHeader", StringType.INSTANCE)
                .addScalar("reqTotalAmt", BigDecimalType.INSTANCE)
                .addScalar("applicationName", StringType.INSTANCE)
                .addScalar("approvedName1", StringType.INSTANCE)
                .addScalar("approvedStatus1", BooleanType.INSTANCE)
                .addScalar("approvedName2", StringType.INSTANCE)
                .addScalar("approvedStatus2", BooleanType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(TicketHeader.class));
        query.setParameter("paramYear", year);
        query.setParameter("paramMonth", month);
        query.setParameter("division", division);
        List results = query.list();
        return results;

    }

}
