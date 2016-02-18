package com.necl.core.daoImpl;

import com.necl.core.dao.UserDao;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.login.controller.HomeController;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findById(long id) throws Exception {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> userList = sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return userList;
    }

    @Override
    public boolean save(User user) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return true;
    }

    @Override
    public boolean update(User user) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return true;
    }

    @Override
    public boolean delete(User user) throws Exception {
        sessionFactory.getCurrentSession().delete(user);
        return true;
    }

    @Override
    public User findBySso(String sso) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
        crit.add(Restrictions.eq("ssoId", sso));
        return (User) crit.uniqueResult();
    }

    @Override
    public List<User> findMailUserApprove(String ticketNumber) {

        String sql = "EXEC CheckApproves :ticketNumber";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("ticketNumber", ticketNumber);

        query.addEntity(User.class);
        List results = query.list();

        return results;
    }

    @Override
    public List<User> getMD() throws Exception {
        String sql = "SELECT   DISTINCT  APP_USER.id, APP_USER.branchId, APP_USER.divisionCode, APP_USER.EMAIL, APP_USER.FIRST_NAME, APP_USER.groupCode, APP_USER.LAST_NAME, \n"
                + "APP_USER.PASSWORD, APP_USER.sectionCode, APP_USER.SSO_ID, APP_USER.STATE, APP_USER.subSectionCode, APP_USER.positionCode\n"
                + "FROM         tblMaster_Position INNER JOIN\n"
                + "APP_USER ON tblMaster_Position.PositionCode = APP_USER.positionCode LEFT OUTER JOIN\n"
                + "tblTicketsH LEFT OUTER JOIN\n"
                + "tblMaster_PermitApproval ON tblTicketsH.Item = tblMaster_PermitApproval.Item ON tblMaster_Position.PositionCode = tblMaster_PermitApproval.PositionCode\n"
                + "WHERE     (APP_USER.positionCode = 'MD') AND (STATE = 'Active')";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addEntity(User.class);
        List results = query.list();

        return results;
    }

    @Override
    public String resetPassword(String oldPassword, String newPassword, String confirm_newpassword) throws Exception {
        String username = HomeController.getPrincipal();

        String sql_select = "SELECT SSO_ID FROM  APP_USER WHERE PASSWORD = :password";
        SQLQuery query_select = sessionFactory.getCurrentSession().createSQLQuery(sql_select);
        query_select.setParameter("password", oldPassword);

        if (query_select.list().size() > 0) {
            if (newPassword.equals(confirm_newpassword)) {
                
                String sql_update = "UPDATE APP_USER\n"
                        + "SET PASSWORD = :password WHERE SSO_ID = :username";
                SQLQuery query_update = sessionFactory.getCurrentSession().createSQLQuery(sql_update);
                query_update.setParameter("password", newPassword);
                query_update.setParameter("username", username);
                query_update.executeUpdate();

                return "Your password has been reset successfully!";
            } else {
                return "Your new password and confirm password do not match!";
            }
        } else {
            return "Password was incorrect!";
        }
    }

   
}
