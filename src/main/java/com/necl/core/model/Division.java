package com.necl.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMaster_Division")
public class Division {

    @Id
    @Column(name = "DivisionCode", length = 3)
    private String divisionCode;

    @Column(name = "DivisionName", length = 50)
    private String divisionName;

    @Column(name = "MaxBudget", precision = 18, scale = 2)
    private String maxBudget;

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getDivisionName() {
        if(this.divisionName.equals(null)){
            this.divisionName = "-";
        }
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

}
