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
    public String index(Model model) {

        try {
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
                number.setShowTicket(ticketHeader.get(i).getShowTicket());
                number2.add(number);
            }
            model.addAttribute("ticketHeaderView", number2);

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

            model.addAttribute("THANIYA", number_thaniya);
            model.addAttribute("BLC", number_blc);
            model.addAttribute("NLC", number_nlc);
            model.addAttribute("AIRPORT", number_airport);
            model.addAttribute("NCC", number_ncc);
            model.addAttribute("LAEMCHABANG", number_laemchabang);
            model.addAttribute("KORAT", number_korat);

            model.addAttribute("month", monthName);
            model.addAttribute("year", year);
            model.addAttribute("areaTab", "all");
            model.addAttribute("active", "viewdata");
            LOGGER.info("viewdata page index ");

            return "viewdata";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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

        model.addAttribute("THANIYA", number_thaniya);
        model.addAttribute("BLC", number_blc);
        model.addAttribute("NLC", number_nlc);
        model.addAttribute("AIRPORT", number_airport);
        model.addAttribute("NCC", number_ncc);
        model.addAttribute("LAEMCHABANG", number_laemchabang);
        model.addAttribute("KORAT", number_korat);
 model.addAttribute("areaTab", "all");
        model.addAttribute("month", monthName);
        model.addAttribute("year", year);
        model.addAttribute("active", "viewdata");
        LOGGER.info("viewdata page index ");

        return "viewdata";
    }
}
