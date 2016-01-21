package com.necl.training.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMaster_TrainingPlan")
public class TrainingPlan implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PlanID",nullable = false)
    private Long planID;
    @Column(name = "PlanDescription", length = 150)
    private String planDescription;
    
//    @OneToOne(mappedBy="trainingPlan")
//    private TicketHTraining ticketHTraining;
//    
//    

    public Long getPlanID() {
        return planID;
    }

    public void setPlanID(Long planID) {
        this.planID = planID;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }
   
    
}
