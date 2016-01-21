package com.necl.training.daoImpl;

import com.necl.core.model.TicketHeader;
import com.necl.training.dao.TicketHTrainingDao;
import com.necl.training.model.TicketHTraining;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketHTrainingDaoImpl implements TicketHTrainingDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean save(TicketHTraining ticketHTraining) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(ticketHTraining);
        return true;
    }

    @Override
    public TicketHTraining findByTicketNo(String ticketNo) throws Exception {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(TicketHTraining.class);
        crit.add(Restrictions.eq("ticketHeader.ticketNo", ticketNo));
        crit.setMaxResults(1);
        return (TicketHTraining) crit.uniqueResult();
    }

    @Override
    public boolean update(TicketHTraining ticketHTraining) throws Exception {

        String sql = "UPDATE tblTicketsHTraining\n"
                + "SET TicketNo = :ticketNo , TypeID = :typeID, PlanID = :planID, StartDate = :startDate, EndDate = :endDate, Place = :place, OrganizeBy = :organizeBy, PaymentID = :paymentID, PaymentDate = :paymentDate, FileParticipant = :fileParticipant, TotalPerson = :totalPerson, CostPerHead = :costPerHead where TicketNo = :ticketNo";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TicketHTraining.class);
        query.setParameter("ticketNo", ticketHTraining.getTicketHeader().getTicketNo());
        query.setParameter("typeID", ticketHTraining.getTrainingType().getTypeID());
        query.setParameter("planID", ticketHTraining.getTrainingPlan().getPlanID());
        query.setParameter("startDate", ticketHTraining.getStartDate());
        query.setParameter("endDate", ticketHTraining.getEndDate());
        query.setParameter("place", ticketHTraining.getPlace());
        query.setParameter("organizeBy", ticketHTraining.getOrganizeBy());
        query.setParameter("paymentID", ticketHTraining.getTrainingPayment().getPaymentID());
        query.setParameter("paymentDate", ticketHTraining.getPaymentDate());
        query.setParameter("fileParticipant", ticketHTraining.getFileParticipant());
        query.setParameter("totalPerson", ticketHTraining.getTotalPerson());
        query.setParameter("costPerHead", ticketHTraining.getCostPerHead());
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean delete(TicketHTraining ticketHTraining) throws Exception {
//        String sql = "DELETE FROM tblTicketsHTraining WHERE TicketNo = :ticketNo";
//        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
//        query.addEntity(TicketHTraining.class);
//        query.setParameter("ticketNo", ticketHTraining.getTicketHeader().getTicketNo());
//        query.executeUpdate();
//        
//        String sql2 = "DELETE FROM tblTicketsDTraining WHERE TicketNo = :ticketNo";
//        SQLQuery query2 = sessionFactory.getCurrentSession().createSQLQuery(sql2);
//        query2.addEntity(TicketHTraining.class);
//        query2.setParameter("ticketNo", ticketHTraining.getTicketHeader().getTicketNo());
//        query2.executeUpdate();
//        
//        
//        String sql3 = "DELETE FROM tblTicketsH WHERE TicketNo = :ticketNo";
//        SQLQuery query3 = sessionFactory.getCurrentSession().createSQLQuery(sql3);
//        query3.addEntity(TicketHTraining.class);
//        query3.setParameter("ticketNo", ticketHTraining.getTicketHeader().getTicketNo());
//        query3.executeUpdate();

        ticketHTraining.getTicketHeader().setTicketFinished("D");
        sessionFactory.getCurrentSession().update(ticketHTraining.getTicketHeader());

        return true;
    }

}
