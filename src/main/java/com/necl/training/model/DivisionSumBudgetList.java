
package com.necl.training.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DivisionSumBudgetList implements Serializable  {
      private List<DivisionSumBudget> divisionSumBudgets = new ArrayList<>();

    public List<DivisionSumBudget> getDivisionSumBudgets() {
        return divisionSumBudgets;
    }

    public void setDivisionSumBudgets(List<DivisionSumBudget> divisionSumBudgets) {
        this.divisionSumBudgets = divisionSumBudgets;
    }

      
}
