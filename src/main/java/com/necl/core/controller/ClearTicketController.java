package com.necl.core.controller;

import com.necl.core.function.CalculateCost;
import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.HandlerFileUpload;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("ticketHeaderCS")
public class ClearTicketController {

    static private Logger LOGGER = LoggerFactory.getLogger(CreateTicketController.class);

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    HandlerFileUpload handlerFileUpload;

    @Autowired
    @Qualifier("mailService")
    SendMailService sendMailService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinChargeCodeService finChargeCodeService;

    @RequestMapping(value = "/clearticket", method = RequestMethod.GET)
    public ModelAndView clearPage(@RequestParam String id) throws Exception {
        ModelAndView model = new ModelAndView();
        if (id.contains("ADV")) {
            String idBranchUser = findBranchUser();
            List<FinanceChargeCode> chargeCodeList = finChargeCodeService.findChargeCodeAsType(idBranchUser, "ADV");
            model.addObject("chargeCode", chargeCodeList);
            model.addObject("ticketHeader", ticketHeaderService.findById(id));
            model.setViewName("clearticket_advance");
            return model;
        } else if (id.contains("ENT")) {
            model.addObject("ticketHeader", ticketHeaderService.findById(id));
            model.setViewName("clearticket");
            return model;
        }
        return null;
    }

    @RequestMapping(value = "/clearticket/preview", method = RequestMethod.POST)
    public String previewTicket(@ModelAttribute("ticketHeader") @Valid TicketHeader ticketHeader, final BindingResult result, RedirectAttributes attr, ModelMap model) {
        try {

            LOGGER.info("savePettyCash is exeuted!");

            if (result.hasErrors()) {
                attr.addFlashAttribute("org.springframework.validation.BindingResult.ticketHeader", result);
                attr.addFlashAttribute("ticketHeader", ticketHeader);
                return "redirect:/clearticket";
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
            TicketHeader ticketHeaderNonClear = ticketHeaderService.findById(ticketHeader.getTicketNo());

            String number_sumAmount;
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            List<TicketDetailNumber> number2 = new ArrayList<>();

            for (int i = 0; i < ticketHeader.getTicketdetail().size(); i++) {

                System.out.println("scscsc" + ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                TicketDetailNumber number = new TicketDetailNumber();
                number.setDescription(ticketHeader.getTicketdetail().get(i).getFinanceChargeCode().getDescription());
                number.setDetail(ticketHeader.getTicketdetail().get(i).getDetail());
                number.setAmount(numFormat.format(ticketHeader.getTicketdetail().get(i).getAmount()));
                number.setPlace(ticketHeader.getTicketdetail().get(i).getPlace());
                number2.add(number);
            }

            setDetailTicketHeaderBeforeSave(ticketHeader, ticketHeaderNonClear.getReqTotalAmt());
            number_sumAmount = numFormat.format(ticketHeader.getReqTotalAmt());
            handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
            //setTicketHeader(ticketHeader, type);
            //handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
            model.addAttribute("number_sumAmount", number_sumAmount);
            model.addAttribute("ticketDetail", number2);

            model.addAttribute("ticketHeader", ticketHeader);
            model.addAttribute("ticketHeaderCS", ticketHeader);
            System.out.println("check1:" + ticketHeader.getTicketNo());

            return "previewclear";
        } catch (Exception e) {
            e.printStackTrace();;
        }

        return null;
    }

    @RequestMapping(value = "/clearticket/save", method = RequestMethod.POST)
    public String saveTicket(@ModelAttribute("ticketHeaderCS") TicketHeader ticketHeader, SessionStatus status) throws Exception {

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
//        ticketHeader.getTicketdetail().stream().forEach((ticketDetail) -> {
//            ticketDetail.setFinanceChargeCode(finChargeCodeService.findById(ticketDetail.getFinanceChargeCode().getId()));
//        });
//        return ticketHeader;
    }

    //    Duplicate Code CreateTicketController
    private void setNameWaitingApprove1(TicketHeader ticketHeader) throws Exception {
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

}
