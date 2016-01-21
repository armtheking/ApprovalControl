/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.daoImpl;

import com.necl.core.dao.SectionDao;
import com.necl.core.model.Section;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SectionDaoImpl implements SectionDao {
    
protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Section findById(String division, String section) throws Exception {
        logger.info("division : "+division+ " Section : "+section);
        String sql = "SELECT DivisionCode, SectionCode, SectionName, SectionBudget, SectionActual "
                + "FROM tblMaster_Section "
                + "WHERE (SectionCode = :section) AND (DivisionCode = :division)";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(Section.class);
        query.setParameter("section", section);
        query.setParameter("division", division);

        List results = query.list();

        return (Section) results.get(0);
    }

}
