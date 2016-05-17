package com.necl.ptc.controller;

import com.necl.core.controller.CreateTicketController;
import com.necl.core.function.CalculateCost;
import com.necl.core.function.RedirectPageByType;
import com.necl.core.function.RunConfigNumber;
import com.necl.core.model.ConfigSystem;
import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.History;
import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.TicketHeaderNumber;
import com.necl.core.model.User;
import com.necl.core.service.ConfigSystemService;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.HandlerFileUpload;
import com.necl.core.service.HistoryService;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketDetailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("ticketHeaderS")
public class PettyCashController {

    @Autowired
    FinChargeCodeService finChargeCodeService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    ConfigSystemService configSystemService;

    @Autowired
    TicketDetailService ticketDetailService;

    @Autowired
    HistoryService historyService;

    @Autowired
    @Qualifier("mailService")
    SendMailService sendMailService;

    @Autowired
    HandlerFileUpload handlerFileUpload;

    @Autowired
    private UserService userService;

    private ConfigSystem configSystem;

    private static final Logger LOGGER = Logger.getLogger(CreateTicketController.class);

    @RequestMapping(value = "/pettycash/preview", method = RequestMethod.POST)
    public ModelAndView previewTicket(@ModelAttribute(value = "ticketHeader") TicketHeader ticketHeader, BindingResult result, RedirectAttributes attr) {
        try {

            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.ticketHeader", result);
                attr.addFlashAttribute("ticketHeader", ticketHeader);
                LOGGER.debug("preview page is hasErrors redirect!");
                return new ModelAndView(RedirectPageByType.getPageByType(ticketHeader.getTicketType()));
            }

            List<FinanceChargeCode> fc = new ArrayList<>();
            for (Iterator<TicketDetail> iter = ticketHeader.getTicketdetail().listIterator(); iter.hasNext();) {
                TicketDetail td = iter.next();
                //ถ้า ไม่ได้เลือก dropdown ให้ลบ row ตัวนั้น
                if (td.getFinanceChargeCode().getId() == 0 || td.getAmount() == null) {
                    iter.remove();
                } else {
                    // หา description แล้วเก็บไว้ใน list
                    FinanceChargeCode financeChargeCode = finChargeCodeService.findById(td.getFinanceChargeCode().getId());
                    fc.add(financeChargeCode);

                }
            }

            for (int i = 0; i < fc.size(); i++) {
                //set ค่าใน list ไว้ใน header
                ticketHeader.getTicketdetail().get(i).setFinanceChargeCode(fc.get(i));
            }
            for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {
                if (ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getAccessCode().equals("218200") && ticketHeader.getTicketdetail().get(i).getAmount().compareTo(BigDecimal.ZERO) > 0) {

                    ticketHeader.getTicketdetail().get(i).setAmount(BigDecimal.ZERO.subtract(ticketHeader.getTicketdetail().get(i).getAmount()));
                }
            }

            BigDecimal sumCost = new BigDecimal("0");;
            for (TicketDetail ticketDe : ticketHeader.getTicketdetail()) {
                sumCost = sumCost.add(ticketDe.getAmount());
            }

            if (sumCost.doubleValue() <= 5000) {

                ticketHeader.setReqTotalAmt(sumCost);
                String number_sumAmount;
                DecimalFormat numFormat;
                numFormat = new DecimalFormat("#,##0.00");

                number_sumAmount = numFormat.format(sumCost);
                for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {
                    System.out.println("ChargeCodeId: " + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getId());
                    System.out.println("Description: " + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                    System.out.println("Amount: " + ticketHeader.getTicketdetail().get(i).getAmount());
                }

                ModelAndView model = new ModelAndView();
                List<TicketDetailNumber> number2 = new ArrayList<>();

                for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {

                    System.out.println("scscsc" + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                    TicketDetailNumber number = new TicketDetailNumber();
                    number.setDescription(ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                    number.setDetail(ticketHeader.getTicketdetail().get(i).getDetail());
                    number.setAmount(numFormat.format(ticketHeader.getTicketdetail().get(i).getAmount()));
                    number.setReceiptNo(ticketHeader.getTicketdetail().get(i).getDescription());
                    number2.add(number);
                }
                setDetailTicketHeaderBeforeSave(ticketHeader);

                handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());

                attr.addFlashAttribute("ticketHeader", ticketHeader);

                model.setViewName("pettycash/pettycashpreview");
                model.addObject("ticketHeader", ticketHeader);
                model.addObject("number_sumAmount", number_sumAmount);
                model.addObject("ticketHeaderS", ticketHeader);
                model.addObject("ticketDetail", number2);
                System.out.println("check3:");
                return model;
            } else {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.ticketHeader", result);
                attr.addFlashAttribute("ticketHeader", ticketHeader);
                LOGGER.debug("preview page is hasErrors redirect!");
                return new ModelAndView(RedirectPageByType.getPageByType(ticketHeader.getTicketType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/pettycash/create", method = RequestMethod.POST)
    public String trainingDetail(@ModelAttribute(value = "ticketHeaderS") TicketHeader ticketHeader) {
        System.out.println("c1+" + ticketHeader.getDetailHeader());
        try {
            setDetailTicketHeaderBeforeSave(ticketHeader);
            System.out.println("c2");
            ticketHeaderService.save(ticketHeader);
            System.out.println("c3");
            setNameWaitingApprove1(ticketHeader);
            System.out.println("c4");
            ticketHeaderService.save(ticketHeader);
            System.out.println("c5");
            configSystemService.update(configSystem);
            System.out.println("c6");
            sendMailService.sendMailUserApprove(ticketHeader);
            System.out.println("c7");
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TicketHeader setDetailTicketHeaderBeforeSave(TicketHeader ticketHeader) {
        try {
            configSystem = configSystemService.findByKey(ticketHeader.getTicketType());
            System.out.println("sout" + ticketHeader.getTicketType());
            configSystem = RunConfigNumber.getNumberTicket(configSystem);

            String numberTicket = configSystem.getConfigMemo();
            System.out.println("number: " + numberTicket);
            //29/12/58

            if (ticketHeader.getTicketFinished().equals("R")) {
                ticketHeader.setTicketFinished("0");
            }

            // Set TicketNumber & Type from database config
            ticketHeader.setTicketNo(numberTicket);
            for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {
                ticketHeader.getTicketdetail().get(i).setTicketHeader(ticketHeader);
            }
            ticketHeader.setTicketType(configSystem.getConfigPrefix());

            // Calculate Cost in founction 
            ticketHeader.setReqTotalAmt(CalculateCost.getTotalCost(ticketHeader));

            System.out.println("total: " + ticketHeader.getReqTotalAmt());
            // Get Username from login
            ticketHeader.setApplicationName(HomeController.getPrincipal());

            // Set ChargeCode each detail 15/12/2015
            ticketHeader.getTicketdetail().stream().forEach((ticketDetail) -> {
                ticketDetail.setFinanceChargeCode(finChargeCodeService.findById(ticketDetail.getFinanceChargeCode().getId()));
            });

            return ticketHeader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setNameWaitingApprove1(TicketHeader ticketHeader) {
        try {

            List<User> userList = userService.findMailUserApprove(ticketHeader.getTicketNo());

            int rule = 1;
            int not_approve2 = 1;
            if (userList.size() == 0) {
//set next approved
                ticketHeader.setApprovedStatus1(true);
                ticketHeader.setTicketFinished("1");
                ticketHeaderService.save(ticketHeader);
                rule = 2;
                userList = userService.findMailUserApprove(ticketHeader.getTicketNo());
                System.out.println("list: " + userList);
                if (userList.size() == 0) {
                    System.out.println("check1:" + userList.size());
                    not_approve2 = 2;
                }

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

            //ถ้าคนแรกหาไม่เจอ แต่คนที่ 2 หาเจอ Approve Step2
            if (rule == 2 && not_approve2 == 1) {

                ticketHeader.setApprovedName2(nameUserapprove);
                ticketHeader.setApprovedName1("-");
                ticketHeader.setApprovedPosition1("-");
            } //ถ้าคนแรกหาไม่เจอ และคนที่2 หาไม่เจอ Approve Step3
            else if (rule == 2 && not_approve2 == 2) {
                if (not_approve2 == 2) {
                    System.out.println("check2:");
                    userList = userService.getMD();
                    nameUserapprove = "";
                    for (User user : userList) {
                        if (nameUserapprove.length() < 1) {
                            nameUserapprove = "Waiting : " + user.getSsoId();
                        } else {
                            nameUserapprove = nameUserapprove + ", " + user.getSsoId();
                        }
                    }
                    System.out.println("userList: " + userList);
                    ticketHeader.setApprovedName1("-");
                    ticketHeader.setApprovedPosition1("-");
                    ticketHeader.setApprovedName2(nameUserapprove);

                }
            } else {
                ticketHeader.setApprovedName1(nameUserapprove);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/pettycash/previous", method = RequestMethod.GET)
    public String previousTicket(@ModelAttribute("ticketHeaderS") @Valid TicketHeader ticketHeader, RedirectAttributes attr, ModelMap model) throws Exception {

        attr.addFlashAttribute("ticketHeader", ticketHeader);
        LOGGER.debug("previous page is hasErrors redirect!");
        return RedirectPageByType.getPageByType(ticketHeader.getTicketType());
    }

    @RequestMapping(value = {"/pettycash/edit"}, method = RequestMethod.POST)
    public ModelAndView editTicketPettyCash(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {

        try {
            TicketHeader ticketHeader2 = new TicketHeader();
            ticketHeader2 = ticketHeaderService.findById(ticketHeader.getTicketNo());

            if (ticketHeader2.getTicketFinished().equals("R")) {
                History history = new History();
                Calendar now = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                if (ticketHeader2.getApprovedStatus1() == true) {
                    if (!ticketHeader2.getApprovedDate1().equals("-")) {
                        Date appDate1 = dateFormat.parse(ticketHeader2.getApprovedDate1());
                        Calendar date = Calendar.getInstance();
                        date.setTime(appDate1);
                        history.setApprovedDate1(date);
                    }

                    history.setApprovedName1(ticketHeader2.getApprovedName1());

                    System.out.println("ch4");
                    history.setApprovedRemark2(ticketHeader2.getApprovedRemark2());
                    history.setApprovedStatus1(ticketHeader2.getApprovedStatus1());
                    history.setApprovedStatus2(ticketHeader2.getApprovedStatus2());
                    history.setApprovedName2(ticketHeader2.getApprovedName2());
                } else {
                    history.setApprovedName1(ticketHeader2.getApprovedName1());
                    history.setApprovedRemark1(ticketHeader2.getApprovedRemark1());
                    history.setApprovedStatus1(ticketHeader2.getApprovedStatus1());
                    history.setApprovedStatus2(ticketHeader2.getApprovedStatus2());
                }

                List<History> findHistory = new ArrayList<>();
                System.out.println("ffff: "+ticketHeader2.getTicketNo());
                findHistory = historyService.findByTicketNo(ticketHeader2.getTicketNo());
                System.out.println("ffff2: "+findHistory.size());
                String revNo = "";
                if (findHistory.size() > 0) {
                    historyService.updateStatus(ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() - 1));
                    revNo = ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size());
                    System.out.println("TicketNo_Size: "+findHistory.size());
                    System.out.println("show_ticket: "+ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() + 1));
                    ticketHeader2.setShowTicket(ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() + 1));
                    System.out.println(">0: " + revNo);

                } else {

                    revNo = ticketHeader.getTicketNo() + "-RV00";
                    ticketHeader.setShowTicket(ticketHeader.getTicketNo() + "-RV01");
                    System.out.println("<=0: " + revNo);
                }
                //  int number = Integer.parseInt(ti);
                history.setTicketRev(revNo);
                history.setStatus(true);

                Calendar date2 = Calendar.getInstance();
                history.setDate(date2);

                history.setReqTotalAmt(ticketHeader2.getReqTotalAmt());
                ticketHeader.getHistory().add(history);
                ticketHeader.getHistory().get(0).setTicketHeader(ticketHeader);
            }

            for (int i = 0; i < ticketHeader2.getTicketdetail().size(); i++) {
                System.out.println("checkWOI: " + ticketHeader2.getTicketdetail().get(i).getId());

                if (ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getId() == 0 || ticketHeader.getTicketdetail().get(i).getAmount() == null) {
                    ticketDetailService.delete(ticketHeader2.getTicketdetail().get(i).getId());
                } else {
                    ticketHeader.getTicketdetail().get(i).setId(ticketHeader2.getTicketdetail().get(i).getId());
                }
            }

            List<FinanceChargeCode> fc = new ArrayList<>();
            for (Iterator<TicketDetail> iter = ticketHeader.getTicketdetail().listIterator(); iter.hasNext();) {
                TicketDetail td = iter.next();
                //ถ้า ไม่ได้เลือก dropdown ให้ลบ row ตัวนั้น
                System.out.println("fcharge: " + td.getFinanceChargeCode().getId());
                if (td.getFinanceChargeCode().getId() == 0 || td.getAmount() == null) {
                    iter.remove();
                } else {
                    // หา description แล้วเก็บไว้ใน list
                    FinanceChargeCode financeChargeCode = finChargeCodeService.findById(td.getFinanceChargeCode().getId());
                    fc.add(financeChargeCode);

                }
            }

            for (int i = 0; i < fc.size(); i++) {
                //set ค่าใน list ไว้ใน header
                System.out.println("i: " + i);
                ticketHeader.getTicketdetail().get(i).setFinanceChargeCode(fc.get(i));
                System.out.println("vv: " + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode());
            }

            if (ticketHeader2.getShowTicket() != null) {
                ticketHeader.setShowTicket(ticketHeader2.getShowTicket());
            }
            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();
            String oldItem = ticketHeader.getItem();
            String oldStatus = ticketHeader.getTicketFinished();
            setDetailTicketHeaderBeforeSave(ticketHeader);
            handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketNo);
            ticketHeader.setTicketNo(ticketNo);
            ticketHeaderService.save(ticketHeader);
            setNameWaitingApprove1(ticketHeader);
            ticketHeaderService.save(ticketHeader);
            if (!oldItem.equals(ticketHeader.getItem()) || oldStatus.equals("R")) {

                sendMailService.sendMailUserApprove(ticketHeader);

            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/pettycash/adminPaySuccess", method = RequestMethod.POST)
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
