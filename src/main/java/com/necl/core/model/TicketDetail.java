/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tblTicketsD")
public class TicketDetail implements Serializable {

//    @Id
//    @Column(name = "de_id", unique = true, nullable = false)
//    @GeneratedValue(generator = "gen")
//    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "ticketHeader"))
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ItemFIN", length = 10)
    private String itemFIN;

    @Column(name = "Description", length = 100)
    private String description;

    @Column(name = "Acc_Code", length = 6)
    private String accountCode;

    @Size(max = 150)
    @Column(name = "RemarkDetail", length = 150)
    private String detail;

    @Column(name = "RemarkDetail1", length = 150)
    private String place;

    @Column(name = "Amount", precision = 18, scale = 2)
    @NotNull(message = "Please enter your amount.")
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ChargeCodeId")
    private FinanceChargeCode financeChargeCode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TicketNo")
    private TicketHeader ticketHeader;

//    @OneToOne(fetch = FetchType.EAGER)
//    @PrimaryKeyJoinColumn
//    private TicketHeader ticketHeader;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemFIN() {
        return itemFIN;
    }

    public void setItemFIN(String itemFIN) {
        this.itemFIN = itemFIN;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public FinanceChargeCode getFinanceChargeCode() {
        return financeChargeCode;
    }

    public void setFinanceChargeCode(FinanceChargeCode financeChargeCode) {
        this.financeChargeCode = financeChargeCode;
    }

    public TicketHeader getTicketHeader() {
        return ticketHeader;
    }

    public void setTicketHeader(TicketHeader ticketHeader) {
        this.ticketHeader = ticketHeader;
    }

 

    

}
