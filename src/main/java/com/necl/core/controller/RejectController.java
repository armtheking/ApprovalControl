package com.necl.core.controller;

import com.necl.core.model.TicketHeader;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reject")
public class RejectController {
    
    @Autowired
    private TicketHeaderService ticketHeaderService;
    
    @Autowired
    @Qualifier("mailService")
    private SendMailService sendMailService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/firstApprove", method = RequestMethod.GET)
    public String rejectFirstApprove(@RequestParam String id, @RequestParam String remark) throws Exception {
        TicketHeader ticketHeader = ticketHeaderService.findById(id);
        ticketHeader.setTicketFinished("R");
        ticketHeader.setApprovedDate1(Calendar.getInstance());
        ticketHeader.setApprovedRemark1(remark + " by " + HomeController.getPrincipal());
        ticketHeaderService.update(ticketHeader);
        sendMailService.sendMailToOwnerTicket(ticketHeader);
        return "redirect:/home";
    }
    
    @RequestMapping(value = "/secondApprove", method = RequestMethod.GET)
    public String rejectSecondApprove(@RequestParam String id, @RequestParam String remark) throws Exception {
        TicketHeader ticketHeader = ticketHeaderService.findById(id);
        ticketHeader.setTicketFinished("R");
        ticketHeader.setApprovedDate2(Calendar.getInstance());
        ticketHeader.setApprovedRemark2(remark + " by " + HomeController.getPrincipal());
//        ticketHeader.setApprovedBy1(null);
//        ticketHeader.setApprovedName1(null);
//        ticketHeader.setApplicationPosition(null);
//        ticketHeader.setApprovedStatus1(false);
//        ticketHeader.setApprovedDate1(null);
//        ticketHeader.setApprovedName2(null);
        ticketHeaderService.update(ticketHeader);
        sendMailService.sendMailToOwnerTicket(ticketHeader);
        if (!ticketHeader.getApprovedName1().equals("-")) {
            sendMailService.sendMailRejectToApproveone(ticketHeader);
        }
        
        return "redirect:/home";
    }
}
