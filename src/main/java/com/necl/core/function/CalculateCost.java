/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.function;

import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketHeader;
import com.necl.training.model.TicketDTraining;
import java.math.BigDecimal;

/**
 *
 * @author C13.207
 */
public class CalculateCost {
    
    CalculateCost() {
    }
    
  private BigDecimal calculateTotalCost(TicketHeader ticketHeader) {
    
        BigDecimal sumCost = new BigDecimal("0");;
        for (TicketDetail ticketDe : ticketHeader.getTicketdetail()) {
            sumCost = sumCost.add(ticketDe.getAmount());
        }
        return sumCost;
    }
    
     private BigDecimal calculateTotalCostTraining(TicketHeader ticketHeader) {
        BigDecimal sumCost = new BigDecimal("0");;
        for (TicketDTraining ticketDe : ticketHeader.getTicketDTraining()) {
            sumCost = sumCost.add(ticketDe.getAmount());
        }
        return sumCost;
    }
    
    
    public static BigDecimal getTotalCost(TicketHeader ticketHeader) {
        return new CalculateCost().calculateTotalCost(ticketHeader);
    }
     public static BigDecimal getTotalCostTraining(TicketHeader ticketHeader) {
        return new CalculateCost().calculateTotalCostTraining(ticketHeader);
    }
    
}
