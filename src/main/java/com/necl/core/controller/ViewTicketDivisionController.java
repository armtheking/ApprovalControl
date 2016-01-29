package com.necl.core.controller;

import com.necl.core.model.TicketHeader;
import com.necl.core.model.TicketHeaderNumber;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/viewdata_all")
public class ViewTicketDivisionController {

    static private Logger LOGGER = LoggerFactory.getLogger(ViewTicketDivisionController.class);

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws Exception {

        String monthName = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String sso_id = HomeController.getPrincipal();

        String division = userService.findBySso(sso_id).getDivisionCode();

        if (month == 1) {
            monthName = "January";
        } else if (month == 2) {
            monthName = "February";
        } else if (month == 3) {
            monthName = "March";
        } else if (month == 4) {
            monthName = "April";
        } else if (month == 5) {
            monthName = "May";
        } else if (month == 6) {
            monthName = "June";
        } else if (month == 7) {
            monthName = "July";
        } else if (month == 8) {
            monthName = "August";
        } else if (month == 9) {
            monthName = "September";
        } else if (month == 10) {
            monthName = "October";
        } else if (month == 11) {
            monthName = "November";
        } else if (month == 12) {
            monthName = "December";
        }

        List<TicketHeader> ticketHeader = new ArrayList<>();
        ticketHeader = ticketHeaderService.findMonthYear(month, year, division);

        DecimalFormat numFormat;
        numFormat = new DecimalFormat("#,##0.00");

        List<TicketHeaderNumber> number2 = new ArrayList<>();

        for (int i = 0; i < ticketHeader.size(); i++) {
            TicketHeaderNumber number = new TicketHeaderNumber();
            number.setTicketNo(ticketHeader.get(i).getTicketNo());
            number.setTicketType(ticketHeader.get(i).getTicketType());
            number.setApplicationDate(ticketHeader.get(i).getApplicationDate());
            number.setDetailHeader(ticketHeader.get(i).getDetailHeader());
            number.setReqTotalAmt(numFormat.format(ticketHeader.get(i).getReqTotalAmt()));
            number.setApplicationName(ticketHeader.get(i).getApplicationName());
            number.setTicketFinished(ticketHeader.get(i).getTicketFinished());
            number.setApprovedName1(ticketHeader.get(i).getApprovedName1());
            number.setApprovedStatus1(ticketHeader.get(i).getApprovedStatus1());
            number.setApprovedName2(ticketHeader.get(i).getApprovedName2());
            number.setApprovedStatus2(ticketHeader.get(i).getApprovedStatus2());

            number2.add(number);
        }

        model.addAttribute("ticketHeaderView", number2);
        model.addAttribute("month", monthName);
        model.addAttribute("year", year);
        model.addAttribute("active", "viewdata");
        LOGGER.info("viewdata page index ");

        return "viewdata";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam int month, @RequestParam int year) throws Exception {
        String monthName = "";
        String sso_id = HomeController.getPrincipal();
        String division = userService.findBySso(sso_id).getDivisionCode();

        if (month == 1) {
            monthName = "January";
        } else if (month == 2) {
            monthName = "February";
        } else if (month == 3) {
            monthName = "March";
        } else if (month == 4) {
            monthName = "April";
        } else if (month == 5) {
            monthName = "May";
        } else if (month == 6) {
            monthName = "June";
        } else if (month == 7) {
            monthName = "July";
        } else if (month == 8) {
            monthName = "August";
        } else if (month == 9) {
            monthName = "September";
        } else if (month == 10) {
            monthName = "October";
        } else if (month == 11) {
            monthName = "November";
        } else if (month == 12) {
            monthName = "December";
        }

        List<TicketHeader> ticketHeader = new ArrayList<>();
        ticketHeader = ticketHeaderService.findMonthYear(month, year, division);

        DecimalFormat numFormat;
        numFormat = new DecimalFormat("#,##0.00");

        List<TicketHeaderNumber> number2 = new ArrayList<>();

        for (int i = 0; i < ticketHeader.size(); i++) {
            TicketHeaderNumber number = new TicketHeaderNumber();
            number.setTicketNo(ticketHeader.get(i).getTicketNo());
            number.setTicketType(ticketHeader.get(i).getTicketType());
            number.setApplicationDate(ticketHeader.get(i).getApplicationDate());
            number.setDetailHeader(ticketHeader.get(i).getDetailHeader());
            number.setReqTotalAmt(numFormat.format(ticketHeader.get(i).getReqTotalAmt()));
            number.setApplicationName(ticketHeader.get(i).getApplicationName());
            number.setTicketFinished(ticketHeader.get(i).getTicketFinished());
            number.setApprovedName1(ticketHeader.get(i).getApprovedName1());
            number.setApprovedStatus1(ticketHeader.get(i).getApprovedStatus1());
            number.setApprovedName2(ticketHeader.get(i).getApprovedName2());
            number.setApprovedStatus2(ticketHeader.get(i).getApprovedStatus2());

            number2.add(number);
        }

        model.addAttribute("ticketHeaderView", number2);
        model.addAttribute("month", monthName);
        model.addAttribute("year", year);
        model.addAttribute("active", "viewdata");
        LOGGER.info("viewdata page index ");

        return "viewdata";
    }
}
