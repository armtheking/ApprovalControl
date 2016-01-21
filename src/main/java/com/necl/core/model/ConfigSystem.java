package com.necl.core.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblConfig")
public class ConfigSystem {

    @Id
    @Column(name = "ConfigDesc", length = 15)
    private String configDesc;

    @Column(name = "ConfigText", length = 100)
    private String configText;

    @Column(name = "ConfigPrefix", length = 5)
    private String configPrefix;

    @Column(name = "ConfigRun", length = 5)
    private String configRun;

    @Column(name = "ConfigDate")
    private Calendar configDate;

    @Column(name = "ConfigNum")
    private Double configNum;

    @Column(name = "ConfigMemo", length = 250)
    private String configMemo;

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public String getConfigText() {
        return configText;
    }

    public void setConfigText(String configText) {
        this.configText = configText;
    }

    public String getConfigPrefix() {
        return configPrefix;
    }

    public void setConfigPrefix(String configPrefix) {
        this.configPrefix = configPrefix;
    }

    public String getConfigRun() {
        return configRun;
    }

    public void setConfigRun(String configRun) {
        this.configRun = configRun;
    }

    public Calendar getConfigDate() {
        return configDate;
    }

    public void setConfigDate(Calendar configDate) {
        this.configDate = configDate;
    }

    public Double getConfigNum() {
        return configNum;
    }

    public void setConfigNum(Double configNum) {
        this.configNum = configNum;
    }

    public String getConfigMemo() {
        return configMemo;
    }

    public void setConfigMemo(String configMemo) {
        this.configMemo = configMemo;
    }

}
