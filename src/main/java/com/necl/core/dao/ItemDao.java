/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.dao;

import com.necl.core.model.Item;
import java.math.BigDecimal;

/**
 *
 * @author C13.207
 */
public interface ItemDao {
    public Item findItemTicket(BigDecimal cost, String ticketType);
}
