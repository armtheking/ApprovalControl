package com.necl.core.daoImpl;

import com.necl.core.dao.BranchDao;
import com.necl.core.model.Branch;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BranchDaoImpl implements BranchDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Branch findByBranchId(String branchID) throws Exception {
        Branch branch = (Branch) sessionFactory.getCurrentSession().get(Branch.class, branchID);
        return branch;
    }
}
