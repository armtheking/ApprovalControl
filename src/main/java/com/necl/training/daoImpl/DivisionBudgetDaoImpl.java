package com.necl.training.daoImpl;

import com.necl.training.dao.DivisionBudgetDao;
import com.necl.training.model.DivisionSumBudget;
import com.necl.training.model.DivisionSumBudgetList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DivisionBudgetDaoImpl implements DivisionBudgetDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List sumBudget() throws Exception {

        String sql = "SELECT DISTINCT DivisionCode, DivisionName, MaxBudget,\n"
                + "(SELECT SUM(ReqTotalAmt) AS SumBudget1\n"
                + "FROM tblTicketsH\n"
                + "WHERE (TicketType = 'TRN') AND (DivisionCode = tblMaster_Division.DivisionCode) AND (TicketsFinished = 'F')) AS SumBudget, MaxBudget -\n"
                + "(SELECT SUM(ReqTotalAmt) AS SumBudget1\n"
                + "FROM tblTicketsH AS tblTicketsH_1\n"
                + "WHERE (TicketType = 'TRN') AND (DivisionCode = tblMaster_Division.DivisionCode) AND (TicketsFinished = 'F')) AS Balance\n"
                + "FROM tblMaster_Division";

        SQLQuery query = (SQLQuery) sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DivisionSumBudget.class);
        List sumlist = query.list();

        System.out.println("sumlist: " + sumlist);

        return sumlist;

    }

    @Override
    public void editMaxBudget(DivisionSumBudgetList divisionSumBudgetList) throws Exception {

        for (int i = 0; i < divisionSumBudgetList.getDivisionSumBudgets().size(); i++) {
            String sql = "UPDATE   tblMaster_Division\n"
                    + "SET MaxBudget  = :maxBudget  WHERE DivisionCode = :divisionCode";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            query.addEntity(DivisionSumBudgetList.class);
            query.setParameter("divisionCode", divisionSumBudgetList.getDivisionSumBudgets().get(i).getDivisionCode());
            query.setParameter("maxBudget", divisionSumBudgetList.getDivisionSumBudgets().get(i).getMaxBudget());

            query.executeUpdate();
        }

    }

}
