/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMaster_Position")
public class Position {

    @Id
    @Column(name = "PositionCode", length = 5)
    private String positionCode;

    @Column(name = "PositionName", length = 50)
    private String positionName;

    @Column(name = "Priority")
    private int priority;

    @Column(name = "PositionMinApprove", precision = 18, scale = 2)
    private BigDecimal positionMinApprove;

    @Column(name = "PositionMaxApprove", precision = 18, scale = 2)
    private BigDecimal positionMaxApprove;

    @Column(name = "StatusApplication")
    private Boolean statusApplication;

    @Column(name = "StatusApproval")
    private Boolean statusApproval;

    @Column(name = "StutusCheck")
    private Boolean statusCheck;

    @Column(name = "StutusApprovalTop")
    private Boolean statusApprovalTop;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BigDecimal getPositionMinApprove() {
        return positionMinApprove;
    }

    public void setPositionMinApprove(BigDecimal positionMinApprove) {
        this.positionMinApprove = positionMinApprove;
    }

    public BigDecimal getPositionMaxApprove() {
        return positionMaxApprove;
    }

    public void setPositionMaxApprove(BigDecimal positionMaxApprove) {
        this.positionMaxApprove = positionMaxApprove;
    }

    public Boolean getStatusApplication() {
        return statusApplication;
    }

    public void setStatusApplication(Boolean statusApplication) {
        this.statusApplication = statusApplication;
    }

    public Boolean getStatusApproval() {
        return statusApproval;
    }

    public void setStatusApproval(Boolean statusApproval) {
        this.statusApproval = statusApproval;
    }

    public Boolean getStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(Boolean statusCheck) {
        this.statusCheck = statusCheck;
    }

    public Boolean getStatusApprovalTop() {
        return statusApprovalTop;
    }

    public void setStatusApprovalTop(Boolean statusApprovalTop) {
        this.statusApprovalTop = statusApprovalTop;
    }

}
