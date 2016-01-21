package com.necl.core.daoImpl;

import com.necl.core.dao.ConfigSystemDao;
import com.necl.core.model.ConfigSystem;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigSystemDaoImpl implements ConfigSystemDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public ConfigSystem findByKey(String key) throws Exception {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ConfigSystem.class);
        crit.add(Restrictions.eq("configDesc", key));
        return (ConfigSystem) crit.uniqueResult();
    }

    @Override
    public boolean update(ConfigSystem configSystem) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(configSystem);
        return true;
    }
    
}
