package com.necl.core.daoImpl;

import com.necl.core.dao.UserProfileDao;
import com.necl.core.model.UserProfile;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userProfileDao")
public class UserProfileImpl implements UserProfileDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<UserProfile> findAll(){
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserProfile.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.addOrder(Order.asc("type"));
        return (List<UserProfile>)crit.list();
    }
     
    public UserProfile findById(int id) {
        UserProfile userProfile = (UserProfile) sessionFactory.getCurrentSession().get(UserProfile.class, id);
        return userProfile;
    }
     
    public UserProfile findByType(String type) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserProfile.class);
        crit.add(Restrictions.eq("type", type));
        return (UserProfile) crit.uniqueResult();
    }
    
}
