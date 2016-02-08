package com.necl.core.model;

import com.necl.training.model.TicketDTraining;
import com.necl.training.model.TicketHTraining;
import com.necl.training.model.TrainingParticipant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "tblTicketsH")
@DynamicUpdate
public class TicketHeader implements Serializable {

    // Saction 1 from create ticket
    @Id
    @Column(name = "TicketNo", length = 14)
    private String ticketNo;

    @Column(name = "TicketType", length = 3)
    private String ticketType;

    @Size(max = 150)
    @Column(name = "DetailHeader", length = 150)
    @NotEmpty
    private String detailHeader;

    @Size(max = 150)
    @Column(name = "RemarkHeader", length = 150)
    private String remarkHeader;

    @Column(name = "RefTicketNo", length = 14)
    private String refTicketNo;

    @Column(name = "DateAlert")
    private int dateAlert;

    @Column(name = "DivisionCode", length = 3)
    private String divisionCode;

    @Column(name = "SectionCode", length = 2)
    private String sectionCode;

    @Column(name = "SubSectionCode", length = 10)
    private String subSectionCode;

    @Column(name = "ApplicationID", length = 12)
    private String applicationID;

    @Column(name = "ApplicationName", length = 150)
    private String applicationName;

    @Column(name = "ApplicationPosition", length = 10)
    private String applicationPosition;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ApplicationDate", insertable = false, updatable = false)
    private Calendar applicationDate;

    @Column(name = "ApplicationStatus", columnDefinition = "BIT default 1", nullable = false)
    private Boolean applicationStatus = true;

    @Column(name = "Item", length = 6)
    private String item;

    @Column(name = "CategoryID", length = 10)
    private String categoryId;

    @Column(name = "SubCategoryID", length = 10)
    private String subCategoryId;

    @Column(name = "TicketsFinished", nullable = false, columnDefinition = "varchar(2) default '0'")
    private String ticketFinished = "0";

    @Column(name = "ReqTotalAmt", precision = 18, scale = 2)
    private BigDecimal reqTotalAmt;

    //Section 2 section approve 1, 2
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

    // Section 3 Payback (Clear Ticket)
    @Column(name = "PayBack", precision = 18, scale = 2)
    private BigDecimal payBack;

    // Section 4 Finance Check By 
    @Column(name = "CheckBy", length = 14)
    private String checkBy;

    @Column(name = "CheckName", length = 150)
    private String checkName;

    @Column(name = "CheckPosition", length = 5)
    private String checkPosition;

    @Column(name = "CheckDate")
    private Calendar checkDate;

    @Column(name = "CheckStatus", columnDefinition = "BIT default 0")
    private Boolean checkStatus = false;

    // Section 5 ReceiverBy (Finance input)
    @Column(name = "ReceiverBy", length = 14)
    private String receiverBy;

    @Column(name = "ReceiverDate")
    private Calendar receiverDate;

    @Column(name = "ReceiverStatus", columnDefinition = "BIT default 0", nullable = false)
    private Boolean receiverStatus = false;

    // Section 6 Paid
    @Column(name = "PaidTotalAmt", precision = 18, scale = 2)
    private BigDecimal paidTotalAmount;

    @Column(name = "PaidStatus", columnDefinition = "BIT default 0", nullable = false)
    private Boolean paidStatus = false;

    @Column(name = "PaidBy", length = 14)
    private String paidBy;

    @Column(name = "PaidRemark", length = 150)
    private String paidRemark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PaidDate")
    private Calendar paidDate;

    @Column(name = "Payment", length = 150)
    private String payment;

    @Column(name = "RemarkOverRequest", length = 150)
    private String remarkOverRequestAmount;

    @Transient
    private CommonsMultipartFile file;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @JoinColumn(name = "TicketNo")
//    @Valid
//    private List<TicketDetail> ticketdetail = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ticketHeader")
   @Fetch(value = FetchMode.SUBSELECT)
    @Valid
    private List<TicketDetail> ticketdetail = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ticketHeader")
   @Fetch(value = FetchMode.SUBSELECT)
    private List<TrainingParticipant> trainingParticipant = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ticketHeader")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TicketDTraining> ticketDTraining = new ArrayList<>();

//    @OneToOne(mappedBy = "ticketHeader", cascade = CascadeType.ALL)
//    private TicketHTraining ticketHTraining;
    @Transient
    private List<MultipartFile> files;

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

    public String getDetailHeader() {
        return detailHeader;
    }

    public void setDetailHeader(String detailHeader) {
        this.detailHeader = detailHeader;
    }

    public String getRemarkHeader() {
        return remarkHeader;
    }

    public void setRemarkHeader(String remarkHeader) {
        this.remarkHeader = remarkHeader;
    }

    public String getRefTicketNo() {
        return refTicketNo;
    }

    public void setRefTicketNo(String refTicketNo) {
        this.refTicketNo = refTicketNo;
    }

    public int getDateAlert() {
        return dateAlert;
    }

    public void setDateAlert(int dateAlert) {
        this.dateAlert = dateAlert;
    }

    public String getDivisionCode() {
        if (this.divisionCode.equals(null)) {
            this.divisionCode = "-";
        }
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getSectionCode() {
        if (this.sectionCode.equals(null)) {
            this.sectionCode = "-";
        }
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSubSectionCode() {
        return subSectionCode;
    }

    public void setSubSectionCode(String subSectionCode) {
        this.subSectionCode = subSectionCode;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getApplicationPosition() {
        return applicationPosition;
    }

    public void setApplicationPosition(String applicationPosition) {
        this.applicationPosition = applicationPosition;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Boolean getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Boolean applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getTicketFinished() {
        return ticketFinished;
    }

    public void setTicketFinished(String ticketFinished) {
        this.ticketFinished = ticketFinished;
    }

    public BigDecimal getReqTotalAmt() {
        return reqTotalAmt;
    }

    public void setReqTotalAmt(BigDecimal reqTotalAmt) {
        this.reqTotalAmt = reqTotalAmt;
    }

    public List<TicketDetail> getTicketdetail() {
        return ticketdetail;
    }

    public void setTicketdetail(List<TicketDetail> ticketdetail) {
        this.ticketdetail = ticketdetail;
    }

    public List<TicketDTraining> getTicketDTraining() {
        return ticketDTraining;
    }

    public void setTicketDTraining(List<TicketDTraining> ticketDTraining) {
        this.ticketDTraining = ticketDTraining;
    }

    public List<TrainingParticipant> getTrainingParticipant() {
        return trainingParticipant;
    }

    public void setTrainingParticipant(List<TrainingParticipant> trainingParticipant) {
        this.trainingParticipant = trainingParticipant;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getApplicationDate() {
        return convertCalendar(this.applicationDate);
    }

    public void setApplicationDate(Calendar applicationDate) {
        this.applicationDate = applicationDate;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

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

    public String getApprovedDate1() {

        return convertCalendar(this.approvedDate1);

    }

    public void setApprovedDate1(Calendar approvedDate1) {
        this.approvedDate1 = approvedDate1;
    }

    public String getApprovedRemark1() {
        if (this.approvedRemark1 == null) {
            this.approvedRemark1 = " - ";
        }
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

    public String getApprovedDate2() {
        return convertCalendar(this.approvedDate2);
    }

    public void setApprovedDate2(Calendar approvedDate2) {
        this.approvedDate2 = approvedDate2;
    }

    public String getApprovedRemark2() {
        if (this.approvedRemark2 == null) {
            this.approvedRemark2 = " - ";
        }
        return approvedRemark2;
    }

    public void setApprovedRemark2(String approvedRemark2) {
        this.approvedRemark2 = approvedRemark2;
    }

    public BigDecimal getPayBack() {
        return payBack;
    }

    public void setPayBack(BigDecimal payBack) {
        this.payBack = payBack;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(String checkPosition) {
        this.checkPosition = checkPosition;
    }

    public Calendar getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Calendar checkDate) {
        this.checkDate = checkDate;
    }

    public Boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getReceiverBy() {
        return receiverBy;
    }

    public void setReceiverBy(String receiverBy) {
        this.receiverBy = receiverBy;
    }

    public Calendar getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Calendar receiverDate) {
        this.receiverDate = receiverDate;
    }

    public Boolean getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(Boolean receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public BigDecimal getPaidTotalAmount() {
        return paidTotalAmount;
    }

    public void setPaidTotalAmount(BigDecimal paidTotalAmount) {
        this.paidTotalAmount = paidTotalAmount;
    }

    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaidRemark() {
        return paidRemark;
    }

    public void setPaidRemark(String paidRemark) {
        this.paidRemark = paidRemark;
    }

    public String getPaidDate() {
        return convertCalendar(this.paidDate);
    }

    public void setPaidDate(Calendar paidDate) {
        this.paidDate = paidDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemarkOverRequestAmount() {
        return remarkOverRequestAmount;
    }

    public void setRemarkOverRequestAmount(String remarkOverRequestAmount) {
        this.remarkOverRequestAmount = remarkOverRequestAmount;
    }

    @PrePersist
    public void prePersist() {
        Calendar now = Calendar.getInstance();
        this.setApplicationDate(now);
    }

    @PreUpdate
    public void preUpdate() {
        Calendar now = Calendar.getInstance();
        this.setApplicationDate(now);
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

    @Override
    public String toString() {
        return "TicketHeader{" + "ticketNo=" + ticketNo + ", ticketType=" + ticketType + ", detailHeader=" + detailHeader + ", remarkHeader=" + remarkHeader + ", refTicketNo=" + refTicketNo + ", dateAlert=" + dateAlert + ", divisionCode=" + divisionCode + ", sectionCode=" + sectionCode + ", subSectionCode=" + subSectionCode + ", applicationID=" + applicationID + ", applicationName=" + applicationName + ", applicationPosition=" + applicationPosition + ", applicationDate=" + applicationDate + ", applicationStatus=" + applicationStatus + ", item=" + item + ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", ticketFinished=" + ticketFinished + ", reqTotalAmt=" + reqTotalAmt + ", approvedBy1=" + approvedBy1 + ", approvedName1=" + approvedName1 + ", approvedPosition1=" + approvedPosition1 + ", approvedStatus1=" + approvedStatus1 + ", approvedDate1=" + approvedDate1 + ", approvedRemark1=" + approvedRemark1 + ", approvedBy2=" + approvedBy2 + ", approvedName2=" + approvedName2 + ", approvedPosition2=" + approvedPosition2 + ", approvedStatus2=" + approvedStatus2 + ", approvedDate2=" + approvedDate2 + ", approvedRemark2=" + approvedRemark2 + ", payBack=" + payBack + ", checkBy=" + checkBy + ", checkName=" + checkName + ", checkPosition=" + checkPosition + ", checkDate=" + checkDate + ", checkStatus=" + checkStatus + ", receiverBy=" + receiverBy + ", receiverDate=" + receiverDate + ", receiverStatus=" + receiverStatus + ", paidTotalAmount=" + paidTotalAmount + ", paidStatus=" + paidStatus + ", paidBy=" + paidBy + ", paidRemark=" + paidRemark + ", paidDate=" + paidDate + ", payment=" + payment + ", remarkOverRequestAmount=" + remarkOverRequestAmount + ", file=" + file + '}';
    }

}
