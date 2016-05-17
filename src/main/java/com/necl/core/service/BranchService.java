package com.necl.core.service;

import com.necl.core.model.Branch;

public interface BranchService {
    public Branch findByBranchId(String branchID) throws Exception;
}
