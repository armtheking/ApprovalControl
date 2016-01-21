package com.necl.advance.controller;

import com.necl.core.controller.CreateTicketController;
import com.necl.core.function.CalculateCost;
import com.necl.core.function.RunConfigNumber;
import com.necl.core.model.ConfigSystem;
import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.ConfigSystemService;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.HandlerFileUpload;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
    @Qualifier("mailService")
    SendMailService sendMailService;

    @Autowired
    HandlerFileUpload handlerFileUpload;

    @Autowired
    private UserService userService;

    private ConfigSystem configSystem;

    @RequestMapping(value = "/advance/preview", method = RequestMethod.POST)
    public ModelAndView previewTicket(@ModelAttribute(value = "ticketHeader") TicketHeader ticketHeader, BindingResult result, RedirectAttributes attr) {
        try {

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
        System.out.println("xx:" + ticketHeader);
        System.out.println("check1+" + ticketHeader.getTicketNo());
        try {

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
            System.out.println("check2");
            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();
            System.out.println("check3:" + ticketNo);
            String oldItem = ticketHeader.getItem();
            System.out.println("check4");
            String oldStatus = ticketHeader.getTicketFinished();
            System.out.println("check5");
            setDetailTicketHeaderBeforeSave(ticketHeader);
            System.out.println("check6: " + ticketNo);
            ticketHeader.setTicketNo(ticketNo);
            System.out.println("check7");
            ticketHeaderService.save(ticketHeader);
            System.out.println("check8");
            setNameWaitingApprove1(ticketHeader);
            System.out.println("check9");
            ticketHeaderService.save(ticketHeader);
            System.out.println("check10");
            if (!oldItem.equals(ticketHeader.getItem()) || oldStatus.equals("R")) {

                sendMailService.sendMailUserApprove(ticketHeader);

            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"editClear"}, method = RequestMethod.POST)
    public ModelAndView editTicketClear(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {
        try {
            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();

            String oldItem = ticketHeader.getItem();

            String oldStatus = ticketHeader.getTicketFinished();

            setDetailTicketHeaderBeforeSave(ticketHeader);

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
               System.out.println("ADV_NAME1: "+ticketHeader.getApplicationName());
            System.out.println("check1:"+ticketHeader.getItem());
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
            System.out.println("ADV_NAME: "+ticketHeader.getApplicationName());
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
       try{
        System.out.println("GGG:"+ticketHeader.getApplicationName());
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
        return "redirect:/home";}
       catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

}
