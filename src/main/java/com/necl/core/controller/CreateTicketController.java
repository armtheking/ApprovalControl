package com.necl.core.controller;

import com.necl.login.controller.HomeController;
import com.necl.core.function.CalculateCost;
import com.necl.core.function.RedirectPageByType;
import com.necl.core.service.HandlerFileUpload;
import com.necl.core.model.TicketHeader;
import com.necl.core.function.RunConfigNumber;
import com.necl.core.model.ConfigSystem;
import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.User;
import com.necl.core.service.ConfigSystemService;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.training.model.TicketHTraining;
import com.necl.training.service.TicketHTrainingService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.apache.log4j.Logger;
//import org.slf4j.Logger;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("ticketHeaderS")
@RequestMapping(value = "/createticket")
public class CreateTicketController {

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    TicketHTrainingService ticketHTrainingService;

    @Autowired
    ConfigSystemService configSystemService;

    @Autowired
    @Qualifier("mailService")
    SendMailService sendMailService;

    @Autowired
    HandlerFileUpload handlerFileUpload;

    @Autowired
    private UserService userService;

    @Autowired
    private FinChargeCodeService finChargeCodeService;

    private ConfigSystem configSystem;

    public CreateTicketController() {
        configSystem = new ConfigSystem();
    }

    private static final Logger LOGGER = Logger.getLogger(CreateTicketController.class);

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public ModelAndView previewTicket(@ModelAttribute("ticketHeader") @Valid TicketHeader ticketHeader, final BindingResult result, RedirectAttributes attr) throws Exception {
//        if (result.hasErrors()) {
//            attr.addFlashAttribute("org.springframework.validation.BindingResult.ticketHeader", result);
//            attr.addFlashAttribute("ticketHeader", ticketHeader);
//            LOGGER.debug("preview page is hasErrors redirect!");
//            return RedirectPageByType.getPageByType(ticketHeader.getTicketType());
//        }

        List<FinanceChargeCode> fc = new ArrayList<>();
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
        model.addObject("ticketDetail", number2);
        model.addObject("ticketHeader", ticketHeader);
        model.addObject("ticketHeaderS", ticketHeader);
        model.setViewName("preview");
        LOGGER.info("preview page is exeuted!");
        return model;

    }

    @RequestMapping(value = "/previous", method = RequestMethod.GET)
    public String previousTicket(@ModelAttribute("ticketHeaderS") @Valid TicketHeader ticketHeader, RedirectAttributes attr, ModelMap model) throws Exception {

        attr.addFlashAttribute("ticketHeader", ticketHeader);
        LOGGER.debug("previous page is hasErrors redirect!");
        return RedirectPageByType.getPageByType(ticketHeader.getTicketType());

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTickets(@ModelAttribute("ticketHeaderS") TicketHeader ticketHeader, SessionStatus status) throws Exception {
        setDetailTicketHeaderBeforeSave(ticketHeader);

        ticketHeaderService.save(ticketHeader);
        LOGGER.info("first save ticket is excuted!");

        setNameWaitingApprove1(ticketHeader);
        ticketHeaderService.save(ticketHeader);
        LOGGER.info("second (add name waiting)save ticket is exeuted!");

        configSystemService.update(configSystem);
        LOGGER.info("thrid update number ticket is exeuted!");

        sendMailService.sendMailUserApprove(ticketHeader);
        LOGGER.info("send mail is exeuted!");

        status.setComplete();
        LOGGER.info("save is exeuted!");
        return "redirect:/home";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showTicket(@RequestParam String id) {
        try {
            LOGGER.info("show is exeuted!");
            ModelAndView model = new ModelAndView();
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            String number_sumAmount;
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            number_sumAmount = numFormat.format(ticketHeader.getReqTotalAmt());

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

            model.addObject("number_sumAmount", number_sumAmount);
            model.addObject("ticketDetail", number2);
            model.addObject("ticketHeader", ticketHeader);
            if (ticketHeader == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("show");
            }

            return model;
        } catch (Exception e) {
            LOGGER.info("show Exception!");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String deleteTicket(@RequestParam String id) {
        try {
            LOGGER.info("delete is exeuted!");
            TicketHeader ticketHeader = ticketHeaderService.findById(id);
            ticketHeaderService.delete(ticketHeader);
            return "redirect:/home";
        } catch (Exception e) {
            LOGGER.info("delete Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/delete_training"}, method = RequestMethod.GET)
    public String deleteTicketTraining(@RequestParam String id) {
        try {
            LOGGER.info("delete is exeuted!");
            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);
            ticketHTrainingService.delete(ticketHTraining);
            return "redirect:/home";
        } catch (Exception e) {
            LOGGER.info("delete Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public ModelAndView editTicket(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {
        try {
            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();

            String oldItem = ticketHeader.getItem();

            String oldStatus = ticketHeader.getTicketFinished();
            LOGGER.info("@@@@@@@@@@@@@@ - - -- - :: " + oldStatus);
            setDetailTicketHeaderBeforeSave(ticketHeader);

            ticketHeader.setTicketNo(ticketNo);
            LOGGER.info("save edit is exeuted!");
            ticketHeaderService.save(ticketHeader);
            LOGGER.info("first save ticket is excuted!");

            setNameWaitingApprove1(ticketHeader);
            ticketHeaderService.save(ticketHeader);

            if (!oldItem.equals(ticketHeader.getItem()) || oldStatus.equals("R")) {

                sendMailService.sendMailUserApprove(ticketHeader);
                LOGGER.info("send mail edit ticket change item is exeuted!");
            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {
            LOGGER.info("save edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/editClear"}, method = RequestMethod.POST)
    public ModelAndView editTicketClear(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {
        try {
            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHeader.getTicketNo();

            String oldItem = ticketHeader.getItem();

            String oldStatus = ticketHeader.getTicketFinished();
            LOGGER.info("@@@@@@@@@@@@@@ - - -- - :: " + ticketHeader.getTicketdetail().get(0).getAmount());
            setDetailTicketHeaderBeforeSave(ticketHeader);

            BigDecimal withdraw = ticketHeaderService.findById(ticketHeader.getRefTicketNo()).getReqTotalAmt();

            BigDecimal payBack = withdraw.subtract(ticketHeader.getReqTotalAmt());
            ticketHeader.setPayBack(payBack);

            ticketHeader.setTicketNo(ticketNo);
            LOGGER.info("save edit is exeuted!");
            ticketHeaderService.save(ticketHeader);
            LOGGER.info("first save ticket is excuted!");
            handlerFileUpload.handleFileUploadToPath(ticketHeader.getFile(), ticketHeader.getTicketNo());
            setNameWaitingApprove1(ticketHeader);
            ticketHeaderService.save(ticketHeader);

            if (!oldItem.equals(ticketHeader.getItem()) || oldStatus.equals("R")) {

                sendMailService.sendMailUserApprove(ticketHeader);
                LOGGER.info("send mail edit ticket change item is exeuted!");
            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {
            LOGGER.info("save edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    private TicketHeader setDetailTicketHeaderBeforeSave(TicketHeader ticketHeader) throws Exception {
        configSystem = configSystemService.findByKey(ticketHeader.getTicketType());
        configSystem = RunConfigNumber.getNumberTicket(configSystem);

        String numberTicket = configSystem.getConfigMemo();

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

        // Get Username from login
        ticketHeader.setApplicationName(HomeController.getPrincipal());

        // Set ChargeCode each detail 15/12/2015
        ticketHeader.getTicketdetail().stream().forEach((ticketDetail) -> {
            ticketDetail.setFinanceChargeCode(finChargeCodeService.findById(ticketDetail.getFinanceChargeCode().getId()));
        });

        return ticketHeader;
    }

//    Duplicate Code ClearTicketController
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

}
