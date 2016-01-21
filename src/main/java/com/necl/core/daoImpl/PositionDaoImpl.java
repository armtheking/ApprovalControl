/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.daoImpl;

import com.necl.core.dao.PositionDao;
import com.necl.core.model.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Saspallow
 */
@Repository
public class PositionDaoImpl implements PositionDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Position findById(String id) throws Exception {
        return (Position) getSession().get(Position.class, id);
    }

}
