package com.necl.training.daoImpl;

import com.necl.training.dao.TrainingParticipantDao;
import com.necl.training.model.TrainingParticipant;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingParticipantDaoImpl implements TrainingParticipantDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean delete(long id) throws Exception {
        String sql = "DELETE FROM tblTrainingParticipant WHERE ID = :id";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(TrainingParticipant.class);
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

}
