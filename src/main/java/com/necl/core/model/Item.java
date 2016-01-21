package com.necl.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMaster_Item")
public class Item implements Serializable {

    @Id
    @Column(name = "Item", length = 6)
    private String item;
    
    @Column(name = "CategoryID", length = 2)
    private String categoryId;
    
    @Column(name = "SubCategoryID", length = 2)
    private String subCategoryId;
    
    @Column(name = "ItemName", length = 150)
    private String itemName;
    
    @Column(name = "ItemMinApprove", precision=18, scale=2, length = 15)
    private BigDecimal itemMinApprove;
    
    @Column(name = "ItemMaxApprove", precision=18, scale=2, length = 15)
    private BigDecimal itemMaxApprove;
    
    @Column(name = "StatusStop", columnDefinition = "BIT default 0", nullable = false)
    private Boolean statusStop;
    
    @Column(name = "ItemPrefix", length = 5)
    private String itemPrefix;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemMinApprove() {
        return itemMinApprove;
    }

    public void setItemMinApprove(BigDecimal itemMinApprove) {
        this.itemMinApprove = itemMinApprove;
    }

    public BigDecimal getItemMaxApprove() {
        return itemMaxApprove;
    }

    public void setItemMaxApprove(BigDecimal itemMaxApprove) {
        this.itemMaxApprove = itemMaxApprove;
    }

    

    public Boolean isStatusStop() {
        return statusStop;
    }

    public void setStatusStop(Boolean statusStop) {
        this.statusStop = statusStop;
    }

    public String getItemPrefix() {
        return itemPrefix;
    }

    public void setItemPrefix(String itemPrefix) {
        this.itemPrefix = itemPrefix;
    }

}
