package com.necl.core.daoImpl;

import com.necl.core.dao.ItemDao;
import com.necl.core.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Item findItemTicket(BigDecimal cost, String ticketType) {

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Item.class);
        
        //Set Min $ Max cost
        Criterion min = Restrictions.ge("itemMaxApprove", cost);
        Criterion max = Restrictions.le("itemMinApprove", cost);
        
        // And Set Type
        Criterion type = Restrictions.eq("itemPrefix", ticketType);
        
        LogicalExpression andExp = Restrictions.and(min, max);
        crit.add(andExp);
        crit.add(type);
        List<Item> list = crit.list();

        return (list.isEmpty() ? null : list.get(0));
    }

}
