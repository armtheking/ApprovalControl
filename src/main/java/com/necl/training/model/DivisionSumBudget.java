package com.necl.training.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblMaster_Division")
public class DivisionSumBudget implements Serializable {

    @Id
    @Column(name = "DivisionCode", length = 3)
    private String divisionCode;
    @Column(name = "DivisionName", length = 50)
    private String divisionName;
    @Column(name = "MaxBudget", precision = 18, scale = 2)
    private BigDecimal maxBudget;
    @Column(name = "SumBudget", precision = 18, scale = 2)
    private BigDecimal sumBudget;
    @Column(name = "Balance", precision = 18, scale = 2)
    private BigDecimal balance;

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

    public BigDecimal getMaxBudget() {
        if (this.maxBudget == null) {
            this.maxBudget = new BigDecimal(0.00);
        }
        return maxBudget;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }

    public BigDecimal getSumBudget() {
        if (this.sumBudget == null) {
            this.sumBudget = new BigDecimal(0.00);
        }
        return sumBudget;
    }

    public void setSumBudget(BigDecimal sumBudget) {
        this.sumBudget = sumBudget;
    }

    public BigDecimal getBalance() {
        if (this.balance == null) {
            this.balance = new BigDecimal(0.00);
        }
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
