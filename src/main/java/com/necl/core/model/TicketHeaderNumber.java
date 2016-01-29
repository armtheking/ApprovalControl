package com.necl.core.model;

import java.io.Serializable;

public class TicketHeaderNumber implements Serializable {

    String ticketNo;
    String ticketType;
    String applicationDate;
    String detailHeader;
    String reqTotalAmt;
    String applicationName;
    String ticketFinished;
    String approvedName1;
    Boolean approvedStatus1;
    String approvedName2;
    Boolean approvedStatus2;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getDetailHeader() {
        return detailHeader;
    }

    public void setDetailHeader(String detailHeader) {
        this.detailHeader = detailHeader;
    }

    public String getReqTotalAmt() {
        return reqTotalAmt;
    }

    public void setReqTotalAmt(String reqTotalAmt) {
        this.reqTotalAmt = reqTotalAmt;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getTicketFinished() {
        return ticketFinished;
    }

    public void setTicketFinished(String ticketFinished) {
        this.ticketFinished = ticketFinished;
    }

    public String getApprovedName1() {
        return approvedName1;
    }

    public void setApprovedName1(String approvedName1) {
        this.approvedName1 = approvedName1;
    }

    public Boolean getApprovedStatus1() {
        return approvedStatus1;
    }

    public void setApprovedStatus1(Boolean approvedStatus1) {
        this.approvedStatus1 = approvedStatus1;
    }

    public String getApprovedName2() {
        return approvedName2;
    }

    public void setApprovedName2(String approvedName2) {
        this.approvedName2 = approvedName2;
    }

    public Boolean getApprovedStatus2() {
        return approvedStatus2;
    }

    public void setApprovedStatus2(Boolean approvedStatus2) {
        this.approvedStatus2 = approvedStatus2;
    }

 
    



}
