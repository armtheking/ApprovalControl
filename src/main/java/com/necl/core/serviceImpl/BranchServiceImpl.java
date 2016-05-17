package com.necl.core.serviceImpl;

import com.necl.core.dao.BranchDao;
import com.necl.core.model.Branch;
import com.necl.core.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BranchServiceImpl implements BranchService{

    @Autowired
    BranchDao branchDao;
    
    @Override
    public Branch findByBranchId(String branchID) throws Exception {
        return branchDao.findByBranchId(branchID);
    }
    
}
