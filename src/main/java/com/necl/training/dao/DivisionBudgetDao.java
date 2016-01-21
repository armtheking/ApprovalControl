package com.necl.training.dao;

import com.necl.training.model.DivisionSumBudgetList;
import java.util.List;

public interface DivisionBudgetDao {
    public List sumBudget()throws Exception;
    public void editMaxBudget(DivisionSumBudgetList divisionSumBudgetList)throws Exception;
}
