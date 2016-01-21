package com.necl.training.service;

import com.necl.training.model.DivisionSumBudgetList;
import java.util.List;

public interface DivisionBudgetService {
        public List sumBudget()throws Exception;
        public void editMaxBudget(DivisionSumBudgetList divisionSumBudgetList)throws Exception;
}
