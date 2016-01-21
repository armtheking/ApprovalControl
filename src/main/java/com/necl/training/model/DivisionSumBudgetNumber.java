package com.necl.training.model;

import java.io.Serializable;

public class DivisionSumBudgetNumber implements Serializable {


    private String divisionCode;
    private String divisionName;
    private String maxBudget;
    private String sumBudget;
    private String balance;

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(String maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getSumBudget() {
        return sumBudget;
    }

    public void setSumBudget(String sumBudget) {
        this.sumBudget = sumBudget;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }



}
