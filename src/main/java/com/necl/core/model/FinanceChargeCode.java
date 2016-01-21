package com.necl.core.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "tblMaster_FinChargeCode")
public class FinanceChargeCode implements Serializable {

    @Id
    @Column(name = "ChargeCodeId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BranchID", length = 2)
    private String branchId;

    @Nationalized
    @Type(type = "org.hibernate.type.StringNVarcharType")
    @Column(name = "ItemFIN", length = 10)
    private String itemFinance;

    @Column(name = "Description", length = 100)
    private String description;

    @Column(name = "Acc_Code", length = 6)
    private String accessCode;

    @Column(name = "Stop")
    private boolean status;

    @Column(name = "Update_Time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getItemFinance() {
        return itemFinance;
    }

    public void setItemFinance(String itemFinance) {
        this.itemFinance = itemFinance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

}
