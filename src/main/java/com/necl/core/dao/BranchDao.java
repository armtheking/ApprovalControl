
package com.necl.core.dao;

import com.necl.core.model.Branch;

public interface BranchDao {
    public Branch findByBranchId(String branchID) throws Exception;
}
