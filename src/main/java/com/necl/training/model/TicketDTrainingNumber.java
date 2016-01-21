package com.necl.training.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketDTrainingNumber implements Serializable {

    private String item;
    private String budgetDetail;
    private String amount;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBudgetDetail() {
        return budgetDetail;
    }

    public void setBudgetDetail(String budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    
    
}
