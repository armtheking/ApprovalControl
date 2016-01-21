
package com.necl.training.model;

import com.necl.core.model.TicketHeader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Table(name = "tblTicketsHTraining")
public class TicketHTraining implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "Place", length = 150)
    private String place;
    @Column(name = "OrganizeBy", length = 150)
    private String organizeBy;
    @Column(name = "PaymentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Column(name = "TotalPerson")
    private Long totalPerson;
    @Column(name = "CostPerHead", precision = 18, scale = 2)
    private BigDecimal costPerHead;
    @Column(name = "FileParticipant", length = 150)
    private String fileParticipant;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TicketNo")
    private TicketHeader ticketHeader;
   
    @OneToOne
    @JoinColumn(name="TypeID")
    private TrainingType trainingType;
    
    @OneToOne 
    @JoinColumn(name="PlanID")
    private TrainingPlan trainingPlan;
    
    @OneToOne 
    @JoinColumn(name="PaymentID")
    private TrainingPayment trainingPayment;
   
    
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="ticketHTraining")
//    @JsonManagedReference
//    private List<TrainingParticipant> trainingParticipant = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


  
    public String getStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }
    
    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrganizeBy() {
        return organizeBy;
    }

    public void setOrganizeBy(String organizeBy) {
        this.organizeBy = organizeBy;
    }

    public String getPaymentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(paymentDate);
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Long totalPerson) {
        this.totalPerson = totalPerson;
    }

    public BigDecimal getCostPerHead() {
        return costPerHead;
    }

    public void setCostPerHead(BigDecimal costPerHead) {
        this.costPerHead = costPerHead;
    }

    public String getFileParticipant() {
        return fileParticipant;
    }

    public void setFileParticipant(String fileParticipant) {
        this.fileParticipant = fileParticipant;
    }
    
    public TrainingPayment getTrainingPayment() {
        return trainingPayment;
    }

    public void setTrainingPayment(TrainingPayment trainingPayment) {
        this.trainingPayment = trainingPayment;
    }

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }


}
