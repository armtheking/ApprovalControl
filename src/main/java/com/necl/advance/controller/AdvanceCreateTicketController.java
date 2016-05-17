package com.necl.advance.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("ticketHeaderS")
public class AdvanceCreateTicketController {

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

    @RequestMapping(value = "/advance/preview", method = RequestMethod.POST)
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
                number2.add(number);
            }
            attr.addFlashAttribute("ticketHeader", ticketHeader);

            model.setViewName("advance/advancepreview");
            model.addObject("ticketHeader", ticketHeader);
            model.addObject("number_sumAmount", number_sumAmount);
            model.addObject("ticketHeaderS", ticketHeader);
            model.addObject("ticketDetail", number2);
            System.out.println("check3:");
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/advance/create", method = RequestMethod.POST)
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
            if (ticketHeader.getTicketFinished().equals("R") && ticketHeader.getTicketNo().contains("-C")) {
                ticketHeader.setTicketFinished("C");
            } else if (ticketHeader.getTicketFinished().equals("R") && !ticketHeader.getTicketNo().contains("-C")) {
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
            return ticketHeader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    Duplicate Code ClearTicketController
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

    @RequestMapping(value = {"/advance/edit"}, method = RequestMethod.POST)
    public ModelAndView editTicketAdvance(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {

        try {

            TicketHeader ticketHeader2 = new TicketHeader();
            ticketHeader2 = ticketHeaderService.findById(ticketHeader.getTicketNo());

            if (ticketHeader2.getTicketFinished().equals("R")) {
                History history = new History();
                System.out.println("checcccc");
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
                findHistory = historyService.findByTicketNo(ticketHeader2.getTicketNo());
                String revNo = "";
                if (findHistory.size() > 0) {
                    historyService.updateStatus(ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() - 1));
                    revNo = ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size());
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
            ticketHeader.setTicketNo(ticketNo);
            ticketHeaderService.save(ticketHeader);
            setNameWaitingApprove1(ticketHeader);
            ticketHeaderService.save(ticketHeader);
            ticketHeaderService.update(ticketHeader);
            if (!oldItem.equals(ticketHeader.getItem()) || oldStatus.equals("R")) {

                sendMailService.sendMailUserApprove(ticketHeader);

            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/advance/editClear"}, method = RequestMethod.POST)
    public ModelAndView editTicketClear(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {
        try {
            TicketHeader ticketHeader2 = new TicketHeader();
            ticketHeader2 = ticketHeaderService.findById(ticketHeader.getTicketNo());

            if (ticketHeader2.getTicketFinished().equals("R")) {
                History history = new History();
                System.out.println("checcccc");
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
                    System.out.println("ffff3");
                    history.setApprovedName1(ticketHeader2.getApprovedName1());
                    history.setApprovedRemark1(ticketHeader2.getApprovedRemark1());
                    history.setApprovedStatus1(ticketHeader2.getApprovedStatus1());
                    history.setApprovedStatus2(ticketHeader2.getApprovedStatus2());
                }

                List<History> findHistory = new ArrayList<>();
                findHistory = historyService.findByTicketNo(ticketHeader.getTicketNo());
                String revNo = "";
                if (findHistory.size() > 0) {
                    historyService.updateStatus(ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() - 1));
                    revNo = ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size());
                    ticketHeader.setShowTicket(ticketHeader.getTicketNo() + "-RV" + String.format("%02d", findHistory.size() + 1));
                    System.out.println(">0: " + revNo);

                } else {
                    System.out.println("ffff4");
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
            if (ticketHeader2.getShowTicket() != null) {
                ticketHeader.setShowTicket(ticketHeader2.getShowTicket());
            }

            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();

            String oldItem = ticketHeader.getItem();

            String oldStatus = ticketHeader.getTicketFinished();

            setDetailTicketHeaderBeforeSave(ticketHeader);
            ticketHeader.setRefTicketNo(ticketHeader2.getRefTicketNo());
            BigDecimal withdraw = ticketHeaderService.findById(ticketHeader.getRefTicketNo()).getReqTotalAmt();
            BigDecimal payBack = withdraw.subtract(ticketHeader.getReqTotalAmt());
            ticketHeader.setPayBack(payBack);

            ticketHeader.setTicketNo(ticketNo);
            ticketHeaderService.save(ticketHeader);
            handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
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

    // Clear
    @RequestMapping(value = "/advance/preview_clear", method = RequestMethod.POST)
    public ModelAndView previewTicketAdvance(@ModelAttribute("ticketHeader") @Valid TicketHeader ticketHeader, final BindingResult result, RedirectAttributes attr) {
        try {
            System.out.println("ADV_NAME1: " + ticketHeader.getApplicationName());
            System.out.println("check1:" + ticketHeader.getItem());
            ModelAndView model = new ModelAndView();
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
            System.out.println("check2");
            for (int i = 0; i < fc.size(); i++) {
                //set ค่าใน list ไว้ใน header
                ticketHeader.getTicketdetail().get(i).setFinanceChargeCode(fc.get(i));
            }
            System.out.println("check3");
            BigDecimal sumCost = new BigDecimal("0");;
            for (TicketDetail ticketDe : ticketHeader.getTicketdetail()) {
                sumCost = sumCost.add(ticketDe.getAmount());
            }
            System.out.println("check4");
            // ticketHeader.setReqTotalAmt(sumCost);
            String number_sumAmount;
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");
            System.out.println("check5");
            number_sumAmount = numFormat.format(sumCost);

            List<TicketDetailNumber> number2 = new ArrayList<>();

            for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {

                System.out.println("scscsc" + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                TicketDetailNumber number = new TicketDetailNumber();
                number.setDescription(ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                number.setDetail(ticketHeader.getTicketdetail().get(i).getDetail());
                number.setAmount(numFormat.format(ticketHeader.getTicketdetail().get(i).getAmount()));
                number2.add(number);
            }
            System.out.println("check6");
            //  attr.addFlashAttribute("ticketHeader", ticketHeader);

            TicketHeader ticketHeaderNonClear = ticketHeaderService.findById(ticketHeader.getTicketNo());
            System.out.println("ADV_NAME: " + ticketHeader.getApplicationName());
            setDetailTicketHeaderBeforeSave(ticketHeader, ticketHeaderNonClear.getReqTotalAmt());
            System.out.println("check7");
            handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
            System.out.println("check8");
            //setTicketHeader(ticketHeader, type);
            //handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
            model.addObject("ticketHeader", ticketHeader);
            model.addObject("number_sumAmount", number_sumAmount);
            model.addObject("ticketHeaderS", ticketHeader);
            model.addObject("ticketDetail", number2);
            model.setViewName("advance/advancepreview_clear");
            return model;
        } catch (Exception e) {
            e.printStackTrace();;
        }

        return null;
    }

    private void setDetailTicketHeaderBeforeSave(TicketHeader ticketHeader, BigDecimal reqTotal) throws Exception {

        ticketHeader.setRefTicketNo(ticketHeader.getTicketNo());
        String numberTicket = ticketHeader.getTicketNo() + "-C";

        ticketHeader.setTicketFinished("C");
        ticketHeader.setTicketNo(numberTicket);
        for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {
            ticketHeader.getTicketdetail().get(i).setTicketHeader(ticketHeader);
        }

        ticketHeader.setReqTotalAmt(CalculateCost.getTotalCost(ticketHeader));
        BigDecimal payBack = reqTotal.subtract(ticketHeader.getReqTotalAmt());
        ticketHeader.setPayBack(payBack);

//         Set ChargeCode each detail 16/12/2015
        ticketHeader.getTicketdetail().stream().forEach((ticketDetail) -> {
            ticketDetail.setFinanceChargeCode(finChargeCodeService.findById(ticketDetail.getFinanceChargeCode().getId()));
        });

//        return ticketHeader;
    }

    @RequestMapping(value = "/advance/save_clear", method = RequestMethod.POST)
    public String saveTicket(@ModelAttribute("ticketHeaderS") TicketHeader ticketHeader, SessionStatus status) {
        try {
            System.out.println("GGG:" + ticketHeader.getApplicationName());
            TicketHeader ticketHeaderNonClear = ticketHeaderService.findById(ticketHeader.getRefTicketNo());

            System.out.println("@" + ticketHeader.toString());
            ticketHeaderService.save(ticketHeader);

            System.out.println("@2");
            setNameWaitingApprove1(ticketHeader);

            System.out.println("@3");
            ticketHeaderService.save(ticketHeader);

            System.out.println("@4");
            ticketHeaderNonClear.setTicketFinished("C");

            System.out.println("@5");
            ticketHeaderService.save(ticketHeaderNonClear);

            System.out.println("@6");
            sendMailService.sendMailUserApprove(ticketHeader);
            status.setComplete();
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/advance/previous", method = RequestMethod.GET)
    public String previousTicket(@ModelAttribute("ticketHeaderS") @Valid TicketHeader ticketHeader, RedirectAttributes attr, ModelMap model) throws Exception {

        attr.addFlashAttribute("ticketHeader", ticketHeader);
        LOGGER.debug("previous page is hasErrors redirect!");
        return RedirectPageByType.getPageByType(ticketHeader.getTicketType());
    }

}
