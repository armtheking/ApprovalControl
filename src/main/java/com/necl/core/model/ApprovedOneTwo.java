package com.necl.core.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ApprovedOneTwo implements Serializable {

    public ApprovedOneTwo() {
    }
    

    @Column(name = "ApprovedBy1", length = 12)
    private String approvedBy1;

    @Column(name = "ApprovedName1", length = 150)
    private String approvedName1;

    @Column(name = "ApprovedPosition1", length = 5)
    private String approvedPosition1;

    @Column(name = "ApprovedStatus1", columnDefinition = "BIT default 0", nullable = false)
    private Boolean approvedStatus1 = false;
   

    @Column(name = "ApprovedDate1")
    private Calendar approvedDate1;

    @Column(name = "ApprovedRemark1", length = 150)
    private String approvedRemark1;

    @Column(name = "ApprovedBy2", length = 12)
    private String approvedBy2;

    @Column(name = "ApprovedName2", length = 150)
    private String approvedName2;

    @Column(name = "ApprovedPosition2", length = 5)
    private String approvedPosition2;
    
    @Column(name = "ApprovedStatus2", columnDefinition = "BIT default 0", nullable = false)
    private Boolean approvedStatus2 = false;

    @Column(name = "ApprovedDate2")
    private Calendar approvedDate2;

    @Column(name = "ApprovedRemark2", length = 150)
    private String approvedRemark2;

    public String getApprovedBy1() {
        return approvedBy1;
    }

    public void setApprovedBy1(String approvedBy1) {
        this.approvedBy1 = approvedBy1;
    }

    public String getApprovedName1() {
        if (this.approvedName1 == null) {
            this.approvedName1 = "Waitiing";
        }
        return approvedName1;
    }

    public void setApprovedName1(String approvedName1) {
        this.approvedName1 = approvedName1;
    }

    public String getApprovedPosition1() {
        return approvedPosition1;
    }

    public void setApprovedPosition1(String approvedPosition1) {
        this.approvedPosition1 = approvedPosition1;
    }

    public Boolean getApprovedStatus1() {
        return approvedStatus1;
    }

    public void setApprovedStatus1(Boolean approvedStatus1) {
        this.approvedStatus1 = approvedStatus1;
    }

    public Calendar getApprovedDate1() {
        return approvedDate1;
    }

    public void setApprovedDate1(Calendar approvedDate1) {
        this.approvedDate1 = approvedDate1;
    }

    public String getApprovedRemark1() {
        return approvedRemark1;
    }

    public void setApprovedRemark1(String approvedRemark1) {
        this.approvedRemark1 = approvedRemark1;
    }

    public String getApprovedBy2() {
        return approvedBy2;
    }

    public void setApprovedBy2(String approvedBy2) {
        this.approvedBy2 = approvedBy2;
    }

    public String getApprovedName2() {
        if (this.approvedName2 == null) {
            this.approvedName2 = "Waitiing";
        }
        return approvedName2;
    }

    public void setApprovedName2(String approvedName2) {
        this.approvedName2 = approvedName2;
    }

    public String getApprovedPosition2() {
        return approvedPosition2;
    }

    public void setApprovedPosition2(String approvedPosition2) {
        this.approvedPosition2 = approvedPosition2;
    }

    public Boolean getApprovedStatus2() {
        return approvedStatus2;
    }

    public void setApprovedStatus2(Boolean approvedStatus2) {
        this.approvedStatus2 = approvedStatus2;
    }

    public Calendar getApprovedDate2() {
        return approvedDate2;
    }

    public void setApprovedDate2(Calendar approvedDate2) {
        this.approvedDate2 = approvedDate2;
    }

    public String getApprovedRemark2() {
        return approvedRemark2;
    }

    public void setApprovedRemark2(String approvedRemark2) {
        this.approvedRemark2 = approvedRemark2;
    }

}
