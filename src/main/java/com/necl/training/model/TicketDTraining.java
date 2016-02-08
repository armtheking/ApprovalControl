package com.necl.training.model;

import com.necl.core.model.TicketHeader;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblTicketsDTraining")
public class TicketDTraining implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Item", length = 150)
    private String item;
    @Column(name = "BudgetDetail", length = 150)
    private String budgetDetail;
    @Column(name = "Amount", precision = 18, scale = 2)
    private BigDecimal amount;
    
    

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TicketNo")
    private TicketHeader ticketHeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getBudgetDetail() {
        return budgetDetail;
    }

    public void setBudgetDetail(String budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

}
