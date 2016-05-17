package com.necl.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tblMaster_Branch")
public class Branch {
    
    public Branch(){
    }
    
    @Id
    @Column(name = "BranchID", length = 2)
    private String branchId;
    
    @Column(name = "BranchCode", length = 5)
    private String branchCode;
    
    @Column(name = "BranchName", length = 50)
    private String branchName;
    
    @Column(name = "Area", length = 15)
    private String area;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    
}
