/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.entertain.controller;

import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.TicketHeaderNumber;
import com.necl.core.model.User;
import com.necl.core.service.FinChargeCodeService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author C13.207
 */
@Controller
@RequestMapping(value = "/entertain")
public class EntertainController {

    static private Logger LOGGER = LoggerFactory.getLogger(EntertainController.class);

    @Autowired
    FinChargeCodeService finChargeCodeService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        //model.addAttribute("user", getPrincipal());
        if (!model.containsAttribute("ticketHeader")) {
            model.addAttribute("ticketHeader", new TicketHeader());
        }
        model.addAttribute("active", "entertain");
        LOGGER.info("first page index ");

        return "entertain/entertain";

    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public ModelAndView editTicket(@RequestParam String id) {
        try {
            LOGGER.info("edit is exeuted!");
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            ModelAndView model = new ModelAndView();
            model.addObject("ticketHeader", ticketHeader);
            LOGGER.info("@@@@@@@@xx@@@@@@ - - -- - :: " + ticketHeader.getTicketFinished());

            model.setViewName("entertain/edit");
            return model;
        } catch (Exception e) {
            LOGGER.info("edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/edit_clear"}, method = RequestMethod.GET)
    public ModelAndView editTicketClear(@RequestParam String id) {
        try {
            LOGGER.info("edit is exeuted!");
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            ModelAndView model = new ModelAndView();
            model.addObject("ticketHeader", ticketHeader);
            LOGGER.info("@@@@@@@@xx@@@@@@ - - -- - :: " + ticketHeader.getTicketFinished());

            model.setViewName("entertain/edit_clear");
            return model;
        } catch (Exception e) {
            LOGGER.info("edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @ModelAttribute
    public void addAttributes(Model model) throws Exception {
        String idBranchUser = findBranchUser();
        List<FinanceChargeCode> chargeCodeList = finChargeCodeService.findChargeCodeAsType(idBranchUser, "ENT");

        model.addAttribute("chargeCode", chargeCodeList);
    }

    private String findBranchUser() throws Exception {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        User user = userService.findBySso(userName);
        return user.getBranchId();
    }

    @RequestMapping(value = {"/adminPay"}, method = RequestMethod.GET)
    public ModelAndView adminPay(@RequestParam String id, @RequestParam String areaTab) {
        try {
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            String number_sumAmount;
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            number_sumAmount = numFormat.format(ticketHeader.getReqTotalAmt());

            List<TicketDetailNumber> number2 = new ArrayList<>();

            for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {

                TicketDetailNumber number = new TicketDetailNumber();

                number.setDescription(ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                number.setPlace(ticketHeader.getTicketdetail().get(i).getPlace());
                number.setDetail(ticketHeader.getTicketdetail().get(i).getDetail());
                number.setAmount(numFormat.format(ticketHeader.getTicketdetail().get(i).getAmount()));
                number2.add(number);

            }
            ModelAndView model = new ModelAndView();

            model.addObject("ticketDetail", number2);
            model.addObject("areaTab", areaTab);
            model.addObject("number_sumAmount", number_sumAmount);
            model.addObject("ticketHeader", ticketHeader);

            model.setViewName("entertain/admin_pay");
            return model;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/adminPaySuccess", method = RequestMethod.POST)
    public ModelAndView adminPaySuccess(@ModelAttribute("ticketHeader") TicketHeader ticketHeader, @RequestParam String areaTab) {
        try {

            ModelAndView model = new ModelAndView();

            TicketHeader ticketHeader2 = ticketHeaderService.findById(ticketHeader.getTicketNo());
            ticketHeader2.setAdminBy(HomeController.getPrincipal());
            ticketHeader2.setPaidByAdmin(ticketHeader.getPaidByAdmin());
            ticketHeaderService.update(ticketHeader2);

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

            List<TicketHeader> ticketHeader3 = new ArrayList<>();
            ticketHeader3 = ticketHeaderService.findMonthYear(month, year, division);

            List<TicketHeader> thaniya = new ArrayList<>();
            thaniya = ticketHeaderService.findMonthYearArea(month, year, division, "THANIYA");

            List<TicketHeader> blc = new ArrayList<>();
            blc = ticketHeaderService.findMonthYearArea(month, year, division, "BLC");

            List<TicketHeader> nlc = new ArrayList<>();
            nlc = ticketHeaderService.findMonthYearArea(month, year, division, "NLC");

            List<TicketHeader> airport = new ArrayList<>();
            airport = ticketHeaderService.findMonthYearArea(month, year, division, "AIRPORT");

            List<TicketHeader> ncc = new ArrayList<>();
            ncc = ticketHeaderService.findMonthYearArea(month, year, division, "NCC");

            List<TicketHeader> laemchabang = new ArrayList<>();
            laemchabang = ticketHeaderService.findMonthYearArea(month, year, division, "LAEMCHABANG");

            List<TicketHeader> korat = new ArrayList<>();
            korat = ticketHeaderService.findMonthYearArea(month, year, division, "KORAT");

            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            List<TicketHeaderNumber> number2 = new ArrayList<>();

            for (int i = 0; i < ticketHeader3.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(ticketHeader3.get(i).getTicketNo());
                number.setTicketType(ticketHeader3.get(i).getTicketType());
                number.setApplicationDate(ticketHeader3.get(i).getApplicationDate());
                number.setDetailHeader(ticketHeader3.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(ticketHeader3.get(i).getReqTotalAmt()));
                number.setApplicationName(ticketHeader3.get(i).getApplicationName());
                number.setTicketFinished(ticketHeader3.get(i).getTicketFinished());
                number.setApprovedName1(ticketHeader3.get(i).getApprovedName1());
                number.setApprovedStatus1(ticketHeader3.get(i).getApprovedStatus1());
                number.setApprovedName2(ticketHeader3.get(i).getApprovedName2());
                number.setApprovedStatus2(ticketHeader3.get(i).getApprovedStatus2());
                number.setShowTicket(ticketHeader3.get(i).getShowTicket());
                number2.add(number);
            }
            model.addObject("ticketHeaderView", number2);

            List<TicketHeaderNumber> number_thaniya = new ArrayList<>();
            for (int i = 0; i < thaniya.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(thaniya.get(i).getTicketNo());
                number.setTicketType(thaniya.get(i).getTicketType());
                number.setApplicationDate(thaniya.get(i).getApplicationDate());
                number.setDetailHeader(thaniya.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(thaniya.get(i).getReqTotalAmt()));
                number.setApplicationName(thaniya.get(i).getApplicationName());
                number.setTicketFinished(thaniya.get(i).getTicketFinished());
                number.setApprovedName1(thaniya.get(i).getApprovedName1());
                number.setApprovedStatus1(thaniya.get(i).getApprovedStatus1());
                number.setApprovedName2(thaniya.get(i).getApprovedName2());
                number.setApprovedStatus2(thaniya.get(i).getApprovedStatus2());
                number.setShowTicket(thaniya.get(i).getShowTicket());
                number_thaniya.add(number);
            }

            List<TicketHeaderNumber> number_blc = new ArrayList<>();
            for (int i = 0; i < blc.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(blc.get(i).getTicketNo());
                number.setTicketType(blc.get(i).getTicketType());
                number.setApplicationDate(blc.get(i).getApplicationDate());
                number.setDetailHeader(blc.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(blc.get(i).getReqTotalAmt()));
                number.setApplicationName(blc.get(i).getApplicationName());
                number.setTicketFinished(blc.get(i).getTicketFinished());
                number.setApprovedName1(blc.get(i).getApprovedName1());
                number.setApprovedStatus1(blc.get(i).getApprovedStatus1());
                number.setApprovedName2(blc.get(i).getApprovedName2());
                number.setApprovedStatus2(blc.get(i).getApprovedStatus2());
                number.setShowTicket(blc.get(i).getShowTicket());
                if (blc.get(i).getPaidByAdmin() == null) {

                } else {

                    number.setPaidByAdmin(numFormat.format(blc.get(i).getPaidByAdmin()));
                }
                if (blc.get(i).getAdminBy() == null) {

                } else {
                    number.setAdminBy(blc.get(i).getAdminBy());
                }
                number_blc.add(number);
            }

            List<TicketHeaderNumber> number_nlc = new ArrayList<>();
            for (int i = 0; i < nlc.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(nlc.get(i).getTicketNo());
                number.setTicketType(nlc.get(i).getTicketType());
                number.setApplicationDate(nlc.get(i).getApplicationDate());
                number.setDetailHeader(nlc.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(nlc.get(i).getReqTotalAmt()));
                number.setApplicationName(nlc.get(i).getApplicationName());
                number.setTicketFinished(nlc.get(i).getTicketFinished());
                number.setApprovedName1(nlc.get(i).getApprovedName1());
                number.setApprovedStatus1(nlc.get(i).getApprovedStatus1());
                number.setApprovedName2(nlc.get(i).getApprovedName2());
                number.setApprovedStatus2(nlc.get(i).getApprovedStatus2());
                number.setShowTicket(nlc.get(i).getShowTicket());
                if (nlc.get(i).getPaidByAdmin() == null) {

                } else {

                    number.setPaidByAdmin(numFormat.format(nlc.get(i).getPaidByAdmin()));
                }
                if (nlc.get(i).getAdminBy() == null) {

                } else {
                    number.setAdminBy(nlc.get(i).getAdminBy());
                }
                number_nlc.add(number);
            }

            List<TicketHeaderNumber> number_airport = new ArrayList<>();
            for (int i = 0; i < airport.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(airport.get(i).getTicketNo());
                number.setTicketType(airport.get(i).getTicketType());
                number.setApplicationDate(airport.get(i).getApplicationDate());
                number.setDetailHeader(airport.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(airport.get(i).getReqTotalAmt()));
                number.setApplicationName(airport.get(i).getApplicationName());
                number.setTicketFinished(airport.get(i).getTicketFinished());
                number.setApprovedName1(airport.get(i).getApprovedName1());
                number.setApprovedStatus1(airport.get(i).getApprovedStatus1());
                number.setApprovedName2(airport.get(i).getApprovedName2());
                number.setApprovedStatus2(airport.get(i).getApprovedStatus2());
                number.setShowTicket(airport.get(i).getShowTicket());
                number_airport.add(number);
            }

            List<TicketHeaderNumber> number_ncc = new ArrayList<>();
            for (int i = 0; i < ncc.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(ncc.get(i).getTicketNo());
                number.setTicketType(ncc.get(i).getTicketType());
                number.setApplicationDate(ncc.get(i).getApplicationDate());
                number.setDetailHeader(ncc.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(ncc.get(i).getReqTotalAmt()));
                number.setApplicationName(ncc.get(i).getApplicationName());
                number.setTicketFinished(ncc.get(i).getTicketFinished());
                number.setApprovedName1(ncc.get(i).getApprovedName1());
                number.setApprovedStatus1(ncc.get(i).getApprovedStatus1());
                number.setApprovedName2(ncc.get(i).getApprovedName2());
                number.setApprovedStatus2(ncc.get(i).getApprovedStatus2());
                number.setShowTicket(ncc.get(i).getShowTicket());
                number_ncc.add(number);
            }

            List<TicketHeaderNumber> number_laemchabang = new ArrayList<>();
            for (int i = 0; i < laemchabang.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(laemchabang.get(i).getTicketNo());
                number.setTicketType(laemchabang.get(i).getTicketType());
                number.setApplicationDate(laemchabang.get(i).getApplicationDate());
                number.setDetailHeader(laemchabang.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(laemchabang.get(i).getReqTotalAmt()));
                number.setApplicationName(laemchabang.get(i).getApplicationName());
                number.setTicketFinished(laemchabang.get(i).getTicketFinished());
                number.setApprovedName1(laemchabang.get(i).getApprovedName1());
                number.setApprovedStatus1(laemchabang.get(i).getApprovedStatus1());
                number.setApprovedName2(laemchabang.get(i).getApprovedName2());
                number.setApprovedStatus2(laemchabang.get(i).getApprovedStatus2());
                number.setShowTicket(laemchabang.get(i).getShowTicket());
                number_laemchabang.add(number);
            }

            List<TicketHeaderNumber> number_korat = new ArrayList<>();
            for (int i = 0; i < korat.size(); i++) {
                TicketHeaderNumber number = new TicketHeaderNumber();
                number.setTicketNo(korat.get(i).getTicketNo());
                number.setTicketType(korat.get(i).getTicketType());
                number.setApplicationDate(korat.get(i).getApplicationDate());
                number.setDetailHeader(korat.get(i).getDetailHeader());
                number.setReqTotalAmt(numFormat.format(korat.get(i).getReqTotalAmt()));
                number.setApplicationName(korat.get(i).getApplicationName());
                number.setTicketFinished(korat.get(i).getTicketFinished());
                number.setApprovedName1(korat.get(i).getApprovedName1());
                number.setApprovedStatus1(korat.get(i).getApprovedStatus1());
                number.setApprovedName2(korat.get(i).getApprovedName2());
                number.setApprovedStatus2(korat.get(i).getApprovedStatus2());
                number.setShowTicket(korat.get(i).getShowTicket());
                number_korat.add(number);
            }

            model.addObject("THANIYA", number_thaniya);
            model.addObject("BLC", number_blc);
            model.addObject("NLC", number_nlc);
            model.addObject("AIRPORT", number_airport);
            model.addObject("NCC", number_ncc);
            model.addObject("LAEMCHABANG", number_laemchabang);
            model.addObject("KORAT", number_korat);

            model.addObject("month", monthName);
            model.addObject("year", year);
            model.addObject("active", "viewdata");

            model.addObject("areaTab", areaTab);
            model.setViewName("viewdata");
            return model;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
