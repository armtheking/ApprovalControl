package com.necl.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tblHistory")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "TicketRev", length = 20)
    private String ticketRev;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date")
    private Calendar date;

    @Column(name = "ApprovedName1", length = 150)
    private String approvedName1;

    @Column(name = "ApprovedStatus1", columnDefinition = "BIT default 0", nullable = false)
    private Boolean approvedStatus1 = false;

    @Column(name = "ApprovedDate1")
    private Calendar approvedDate1;

    @Column(name = "ApprovedRemark1", length = 150)
    private String approvedRemark1;

    @Column(name = "ApprovedName2", length = 150)
    private String approvedName2;

    @Column(name = "ApprovedStatus2", columnDefinition = "BIT default 0", nullable = false)
    private Boolean approvedStatus2 = false;

    @Column(name = "ApprovedDate2")
    private Calendar approvedDate2;

    @Column(name = "ApprovedRemark2", length = 150)
    private String approvedRemark2;

    @Column(name = "ReqTotalAmt", precision = 18, scale = 2)
    private BigDecimal reqTotalAmt;

    @Column(name = "Status", columnDefinition = "BIT default 0", nullable = false)
    private Boolean status = false;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TicketNo")
    private TicketHeader ticketHeader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketRev() {
        return ticketRev;
    }

    public void setTicketRev(String ticketRev) {
        this.ticketRev = ticketRev;
    }

    public String getDate() {
        return convertCalendar(this.date);
    }

    public void setDate(Calendar date) {
        this.date = date;
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

    public String getApprovedDate1() {
        return convertCalendar(this.approvedDate1);
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

    public String getApprovedDate2() {
        return convertCalendar(this.approvedDate2);
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

    public BigDecimal getReqTotalAmt() {
        return reqTotalAmt;
    }

    public void setReqTotalAmt(BigDecimal reqTotalAmt) {
        this.reqTotalAmt = reqTotalAmt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

    private String convertCalendar(Calendar calendar) {
        String formatted = null;
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
        if (calendar != null) {
            formatted = format1.format(calendar.getTime());

        } else {
            formatted = "-";
        }

        return formatted;
    }
}
