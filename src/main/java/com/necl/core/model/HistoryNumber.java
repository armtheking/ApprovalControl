
package com.necl.core.model;

import java.io.Serializable;

public class HistoryNumber implements Serializable{
    private String ticketRev;
    private String date;
    private String approvedName1;
    private String approvedRemark1;
    private String approvedName2;
    private String approvedRemark2;
    private String reqTotalAmt;

    public String getTicketRev() {
        return ticketRev;
    }

    public void setTicketRev(String ticketRev) {
        this.ticketRev = ticketRev;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApprovedName1() {
        return approvedName1;
    }

    public void setApprovedName1(String approvedName1) {
        this.approvedName1 = approvedName1;
    }

    public String getApprovedRemark1() {
        return approvedRemark1;
    }

    public void setApprovedRemark1(String approvedRemark1) {
        this.approvedRemark1 = approvedRemark1;
    }

    public String getApprovedName2() {
        return approvedName2;
    }

    public void setApprovedName2(String approvedName2) {
        this.approvedName2 = approvedName2;
    }

    public String getApprovedRemark2() {
        return approvedRemark2;
    }

    public void setApprovedRemark2(String approvedRemark2) {
        this.approvedRemark2 = approvedRemark2;
    }

  

    public String getReqTotalAmt() {
        return reqTotalAmt;
    }

    public void setReqTotalAmt(String reqTotalAmt) {
        this.reqTotalAmt = reqTotalAmt;
    }
    
    
    
}
