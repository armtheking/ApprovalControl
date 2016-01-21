/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMaster_Section")
public class Section implements Serializable {

    public Section() {
    }

    public Section(String divisionCode, String sectionCode) {
        this.divisionCode = divisionCode;
        this.sectionCode = sectionCode;
    }

    public Section(String divisionCode, String sectionCode, String sectionName, String sectionBudget, String sectionActual) {
        this.divisionCode = divisionCode;
        this.sectionCode = sectionCode;
        this.sectionName = sectionName;
        this.sectionBudget = sectionBudget;
        this.sectionActual = sectionActual;
    }

    @Id
    @Column(name = "DivisionCode", length = 3)
    private String divisionCode;

    @Id
    @Column(name = "SectionCode", length = 2)
    private String sectionCode;

    @Column(name = "SectionName", length = 50)
    private String sectionName;

    @Column(name = "SectionBudget", precision = 18, scale = 4)
    private String sectionBudget;

    @Column(name = "SectionActual", precision = 18, scale = 4)
    private String sectionActual;

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSectionName() {

        if (this.sectionName == null) {
            this.sectionName = "Empty";
        }
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionBudget() {
        return sectionBudget;
    }

    public void setSectionBudget(String sectionBudget) {
        this.sectionBudget = sectionBudget;
    }

    public String getSectionActual() {
        return sectionActual;
    }

    public void setSectionActual(String sectionActual) {
        this.sectionActual = sectionActual;
    }

}
