package com.necl.core.controller;

import com.necl.login.controller.HomeController;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import java.util.Calendar;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/approve")
public class ApproveController {

    @Autowired
    private TicketHeaderService ticketHeaderService;

    @Autowired
    @Qualifier("mailService")
    private SendMailService sendMailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/personone*", method = RequestMethod.GET)
    public String approvePersonOne(@RequestParam String id) throws Exception {
        TicketHeader ticketHeader = ticketHeaderService.findById(id);
        ticketHeader.setTicketFinished("1");

        ticketHeaderService.update(addUserApprovedOne(ticketHeader));
        ticketHeader.setApprovedName2(setNameWaitingApprove1(ticketHeader));
        ticketHeaderService.update(ticketHeader);
        sendMailService.sendMailUserApprove(ticketHeader);
        return "redirect:/home";
    }

    @RequestMapping(value = "/persontwo", method = RequestMethod.GET)
    public String approvePersonTwo(@RequestParam String id) throws Exception {
        TicketHeader ticketHeader = ticketHeaderService.findById(id);
        ticketHeader.setTicketFinished("P");
        ticketHeaderService.update(addUserApprovedTwo(ticketHeader));
        sendMailService.sendMailToOwnerTicket(ticketHeader);
        return "redirect:/home";
    }

    private TicketHeader addUserApprovedOne(TicketHeader ticketHeader) throws Exception {
        String ssid = HomeController.getPrincipal();
        User user = userService.findBySso(ssid);

        ticketHeader.setApprovedBy1(String.valueOf(user.getId()));
        ticketHeader.setApprovedName1(user.getSsoId());
        ticketHeader.setApprovedPosition1(user.getPositionCode());
        ticketHeader.setApprovedStatus1(true);
        ticketHeader.setApprovedDate1(Calendar.getInstance());

        return ticketHeader;

    }

    private TicketHeader addUserApprovedTwo(TicketHeader ticketHeader) throws Exception {
        String ssid = HomeController.getPrincipal();
        User user = userService.findBySso(ssid);

        ticketHeader.setApprovedBy2(String.valueOf(user.getId()));
        ticketHeader.setApprovedName2(user.getSsoId());
        ticketHeader.setApprovedPosition2(user.getPositionCode());
        ticketHeader.setApprovedStatus2(true);
        ticketHeader.setApprovedDate2(Calendar.getInstance());

        return ticketHeader;

    }

    private String setNameWaitingApprove1(TicketHeader ticketHeader) throws Exception {
        List<User> userList = userService.findMailUserApprove(ticketHeader.getTicketNo());

        int rule = 1;
        if (userList.size() == 0 && ticketHeader.getReqTotalAmt().doubleValue() > 5000) {
            System.out.println("check!!");
            rule = 2;
        }

        if (rule == 2) {
        
            userList =   userService.getMD();
            System.out.println("userList: "+userList);
        }

        String nameUserapprove = "";
        for (User user : userList) {
            if (nameUserapprove.length() < 1) {
                nameUserapprove = "Waiting : " + user.getSsoId();
            } else {
                nameUserapprove = nameUserapprove + ", " + user.getSsoId();
            }
        }

        if (nameUserapprove.isEmpty() || !nameUserapprove.contains("Waiting")) {
            nameUserapprove = "Empty";
        }

        return nameUserapprove;

    }

    public void printReport() {
    }
}
