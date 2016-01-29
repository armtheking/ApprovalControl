package com.necl.training.controller;

import com.necl.core.function.CalculateCost;
import com.necl.core.function.RedirectPageByType;
import com.necl.core.service.HandlerFileUpload;
import com.necl.core.model.TicketHeader;
import com.necl.core.function.RunConfigNumber;
import com.necl.core.model.ConfigSystem;
import com.necl.core.model.User;
import com.necl.core.service.ConfigSystemService;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import com.necl.training.model.DivisionSumBudget;
import com.necl.training.model.DivisionSumBudgetList;
import com.necl.training.model.TicketDTraining;
import com.necl.training.model.TicketDTrainingNumber;
import com.necl.training.model.TicketHTraining;
import com.necl.training.model.TrainingPayment;
import com.necl.training.model.TrainingPlan;
import com.necl.training.model.TrainingType;
import com.necl.training.service.DivisionBudgetService;
import com.necl.training.service.TicketHTrainingService;
//import com.necl.training.service.TrainingBudgetDescriptionService;
import com.necl.training.service.TrainingPaymentService;
import com.necl.training.service.TrainingPlanService;
import com.necl.training.service.TrainingTypeService;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("ticketHTrainingS")
public class TrainingCreateTicketController extends HttpServlet {

    @Autowired
    ConfigSystemService configSystemService;

    @Autowired
    HandlerFileUpload handlerFileUpload;

    @Autowired
    TrainingTypeService trainingTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    @Qualifier("mailService")
    SendMailService sendMailService;

    @Autowired
    TrainingPlanService trainingPlanService;

    @Autowired
    TrainingPaymentService trainingPaymentService;

//    @Autowired
//    TicketHeaderService ticketHService;
    @Autowired
    TicketHTrainingService ticketHTrainingService;

    @Autowired
    DivisionBudgetService divisionBudgetService;

    private ConfigSystem configSystem;

    private static final Logger LOGGER = Logger.getLogger(TrainingCreateTicketController.class);

//    @RequestMapping(value = "/training/selectType", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    List<TrainingBudgetDescription> selectType(HttpServletRequest request) throws Exception {
//        Long ID = Long.parseLong(request.getParameter("typeID"));
//        List<TrainingBudgetDescription> listBudget = trainingBudgetDescriptionService.findByTypeID(ID);
//        System.out.println("size: " + listBudget.size());
//        for (int i = 0; i < listBudget.size(); i++) {
//            System.out.println("xxx" + listBudget.get(i).getBudgetDescription());
//        }
//        
//        return listBudget;
//    }
    @RequestMapping(value = "/training/createticket", method = RequestMethod.POST)
    public ModelAndView createTicket(@ModelAttribute(value = "ticketHTraining") TicketHTraining ticketHTraining, BindingResult result, RedirectAttributes attr, final @RequestParam CommonsMultipartFile mainfile, final @RequestParam CommonsMultipartFile listfile) throws Exception {
         if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.ticketHeader", result);
            attr.addFlashAttribute("ticketHeader", ticketHTraining);
            LOGGER.debug("preview page is hasErrors redirect!");
            return new ModelAndView(RedirectPageByType.getPageByType(ticketHTraining.getTicketHeader().getTicketType()));
        }

        String username = HomeController.getPrincipal();

        handlerFileUpload.handleFileUploadToPath(mainfile, username);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        String keyFind = "PATH";
        ConfigSystem configSystem = configSystemService.findByKey(keyFind);
        String saveDirectory = configSystem.getConfigText() + yearInString;

        if (!listfile.isEmpty()) {
            File file = new File(saveDirectory + File.separator + username + "-Participant.pdf");
            file.delete();
            ticketHTraining.setFileParticipant("Y");
            handlerFileUpload.handleFileUploadToPath(listfile, username + "-Participant");
        }

        BigDecimal sumCost = new BigDecimal("0");;
        for (TicketDTraining ticketDe : ticketHTraining.getTicketHeader().getTicketDTraining()) {
            sumCost = sumCost.add(ticketDe.getAmount());
        }

        BigDecimal head = new BigDecimal(ticketHTraining.getTotalPerson());

        ticketHTraining.setCostPerHead(sumCost.divide(head, 2, RoundingMode.CEILING));

        ticketHTraining.getTicketHeader().setReqTotalAmt(sumCost);
        String number_sumAmount;
        String number_costPerHead;
        DecimalFormat numFormat;
        numFormat = new DecimalFormat("#,##0.00");

        number_sumAmount = numFormat.format(sumCost);
        number_costPerHead = numFormat.format(ticketHTraining.getCostPerHead());

        attr.addFlashAttribute("ticketHTraining", ticketHTraining);
        TrainingType typeD = trainingTypeService.findByTypeID(ticketHTraining.getTrainingType().getTypeID());
        ticketHTraining.setTrainingType(typeD);
        TrainingPlan planD = trainingPlanService.findByPlanID(ticketHTraining.getTrainingPlan().getPlanID());
        ticketHTraining.setTrainingPlan(planD);
        TrainingPayment paymentD = trainingPaymentService.findByPaymentID(ticketHTraining.getTrainingPayment().getPaymentID());
        ticketHTraining.setTrainingPayment(paymentD);

        ModelAndView model = new ModelAndView();

        List<TicketDTrainingNumber> number2 = new ArrayList<>();

        for (int i = 0; i < ticketHTraining.getTicketHeader().getTicketDTraining().size(); i++) {
            TicketDTrainingNumber number = new TicketDTrainingNumber();
            number.setItem(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getItem());
            number.setBudgetDetail(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getBudgetDetail());
            number.setAmount(numFormat.format(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getAmount()));
            number2.add(number);
        }

        model.setViewName("training/trainingpreview");
        model.addObject("ticketHTraining", ticketHTraining);
        model.addObject("number_sumAmount", number_sumAmount);
        model.addObject("number_costPerHead", number_costPerHead);
        model.addObject("ticketHTrainingS", ticketHTraining);
        model.addObject("ticketDTraining", number2);
        System.out.println("check3:");
        return model;
    }

    @RequestMapping(value = "/training/previous", method = RequestMethod.GET)
    public ModelAndView previousTicket(@ModelAttribute("ticketHTrainingS") TicketHTraining ticketHTraining, RedirectAttributes attr) throws Exception {
        System.out.println("check1");
        ModelAndView model = new ModelAndView();
        model.addObject("ticketHTraining", ticketHTraining);
//        attr.addFlashAttribute("ticketHTraining", ticketHTraining);
        System.out.println("check2");
        model.setViewName("training/training");
        LOGGER.debug("previous page is hasErrors redirect!");
        System.out.println("check3" + ticketHTraining.getTicketHeader().getTicketType());
        return model;

    }

    @RequestMapping(value = "/training/success", method = RequestMethod.POST, params = {"submit"})
    public String trainingDetail(@ModelAttribute(value = "ticketHTrainingS") TicketHTraining ticketHTraining) throws Exception {

        setDetailTicketHeaderBeforeSave(ticketHTraining);

        ticketHTrainingService.save(ticketHTraining);

        setNameWaitingApprove1(ticketHTraining);

        ticketHTrainingService.save(ticketHTraining);

        configSystemService.update(configSystem);

        sendMailService.sendMailUserApprove(ticketHTraining.getTicketHeader());

        return "redirect:/home";
    }

    @RequestMapping(value = "/training/success", method = RequestMethod.POST, params = {"back"})
    public ModelAndView backTraningForm(@ModelAttribute(value = "ticketHeaderS") TicketHeader ticketHeader) throws Exception {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketHeader", ticketHeader);
        model.setViewName("training/training");
        return model;
    }

    @RequestMapping(value = "/training/edit", method = RequestMethod.POST)
    public ModelAndView editTicket(@ModelAttribute(value = "ticketHTraining") TicketHTraining ticketHTraining, BindingResult result, RedirectAttributes attr, final @RequestParam CommonsMultipartFile mainfile, final @RequestParam CommonsMultipartFile listfile) throws Exception {
        System.out.println("typeID" + ticketHTraining.getTrainingType().getTypeID());
        try {

            // การ edit ticket ไม่จำเป้นต้อง run ticket ใหม่
            String ticketNo = ticketHTraining.getTicketHeader().getTicketNo();
            String oldItem = ticketHTraining.getTicketHeader().getItem();
            
            
             handlerFileUpload.handleFileUploadToPath(mainfile, ticketNo);


            if (!listfile.isEmpty()) {
                ticketHTraining.setFileParticipant("Y");
                handlerFileUpload.handleFileUploadToPath(listfile, ticketNo + "-Participant");
            }
        
            setDetailTicketHeaderBeforeSave(ticketHTraining);
            ticketHTraining.getTicketHeader().setTicketNo(ticketNo);
            LOGGER.info("save edit is exeuted!");
            System.out.println("OldItem:" + oldItem);

            System.out.println("organizeby: " + ticketHTraining.getOrganizeBy());

            BigDecimal sumCost = new BigDecimal("0");;
            for (TicketDTraining ticketDe : ticketHTraining.getTicketHeader().getTicketDTraining()) {
                System.out.println("amount: " + ticketDe.getAmount());
                sumCost = sumCost.add(ticketDe.getAmount());
            }

            BigDecimal head = new BigDecimal(ticketHTraining.getTotalPerson());
            ticketHTraining.getTicketHeader().setReqTotalAmt(sumCost);
            ticketHTraining.setCostPerHead(sumCost.divide(head, 2, RoundingMode.CEILING));

            ticketHTrainingService.update(ticketHTraining);

            System.out.println("organizeby2: " + ticketHTraining.getOrganizeBy());
            LOGGER.info("first save ticket is excuted!");

            setNameWaitingApprove1(ticketHTraining);
            ticketHeaderService.save(ticketHTraining.getTicketHeader());

            if (!oldItem.equals(ticketHTraining.getTicketHeader().getItem())) {
                sendMailService.sendMailUserApprove(ticketHTraining.getTicketHeader());
                LOGGER.info("send mail edit ticket change item is exeuted!");
            }

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {
            LOGGER.info("save edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/training/editBudgetSuccess", method = RequestMethod.POST)
    public String editeditBudgetSuccess(@ModelAttribute("divisionSumBudgetList") DivisionSumBudgetList divisionSumBudgetList) {

        try {
            System.out.println("eiei");

            for (int i = 0; i < divisionSumBudgetList.getDivisionSumBudgets().size(); i++) {
                System.out.println("divisionCode:" + divisionSumBudgetList.getDivisionSumBudgets().get(i).getDivisionCode());
                System.out.println("maxBudget:" + divisionSumBudgetList.getDivisionSumBudgets().get(i).getMaxBudget());
            }

            divisionBudgetService.editMaxBudget(divisionSumBudgetList);

            return "redirect:viewBudget";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TicketHTraining setDetailTicketHeaderBeforeSave(TicketHTraining ticketHTraining) throws Exception {

        configSystem = configSystemService.findByKey("TRN");
        configSystem = RunConfigNumber.getNumberTicket(configSystem);

        String numberTicket = configSystem.getConfigMemo();

        // Set TicketNumber & Type from database config
        ticketHTraining.getTicketHeader().setTicketNo(numberTicket);
        ticketHTraining.getTicketHeader().setTicketType(configSystem.getConfigPrefix());
        ticketHTraining.getTicketHeader().setApplicationDate(Calendar.getInstance());
//        ticketHeader.getTicketHTraining().setTicketHeader(ticketHeader);
        // Calculate Cost in founction

        ticketHTraining.getTicketHeader().setReqTotalAmt(CalculateCost.getTotalCostTraining(ticketHTraining.getTicketHeader()));
        // Get Username from login
        ticketHTraining.getTicketHeader().setApplicationName(HomeController.getPrincipal());
        String keyFind = "PATH";
        ConfigSystem configSystem2 = configSystemService.findByKey(keyFind);
        String saveDirectory = configSystem2.getConfigText() + configSystem2.getConfigPrefix();

        File folder = new File(saveDirectory + HomeController.getPrincipal() + ".pdf");
        System.out.println("numberTicket: " + numberTicket);
        String name = numberTicket.replace("/", "_");
        System.out.println("name: " + name);
        folder.renameTo(new File(saveDirectory + name + ".pdf"));

        File folder2 = new File(saveDirectory + HomeController.getPrincipal() + "-Participant.pdf");
        String name2 = numberTicket.replace("/", "_");
        folder2.renameTo(new File(saveDirectory + name2 + "-Participant.pdf"));

        System.out.println("folder2: " + folder);
        System.out.println("check");

        return ticketHTraining;
    }

    private void setNameWaitingApprove1(TicketHTraining ticketHTraining) throws Exception {
        List<User> userList = userService.findMailUserApprove(ticketHTraining.getTicketHeader().getTicketNo());
        int rule = 1;
        int not_approve2 = 1;
        if (userList.size() == 0) {
//set next approved
            ticketHTraining.getTicketHeader().setApprovedStatus1(true);
            ticketHTraining.getTicketHeader().setTicketFinished("1");
            ticketHTrainingService.save(ticketHTraining);
            rule = 2;
            userList = userService.findMailUserApprove(ticketHTraining.getTicketHeader().getTicketNo());
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

            ticketHTraining.getTicketHeader().setApprovedName2(nameUserapprove);
            ticketHTraining.getTicketHeader().setApprovedName1("-");
            ticketHTraining.getTicketHeader().setApprovedPosition1("-");
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
                ticketHTraining.getTicketHeader().setApprovedName1("-");
                ticketHTraining.getTicketHeader().setApprovedPosition1("-");
                ticketHTraining.getTicketHeader().setApprovedName2(nameUserapprove);

            }
        } else {
            ticketHTraining.getTicketHeader().setApprovedName1(nameUserapprove);
        }

    }

//    private void setNameWaitingApprove1_edit(TicketHeader ticketHeader) throws Exception {
//        List<User> userList = userService.findMailUserApprove(ticketHeader.getTicketNo());
//        int rule = 1;
//        int not_approve2 = 1;
//        if (userList.size() == 0) {
//
//            ticketHeader.setApprovedStatus1(true);
//            ticketHeader.setTicketFinished("1");
//            ticketHeaderService.save(ticketHeader);
//            rule = 2;
//
//            userList = userService.findMailUserApprove(ticketHeader.getTicketNo());
//            System.out.println("list: " + userList);
//            if (userList.size() == 0) {
//                System.out.println("check1:" + userList.size());
//                not_approve2 = 2;
//            }
//        }
//        userList = userService.findMailUserApprove(ticketHeader.getTicketNo());
//
//        String nameUserapprove = "";
//        for (User user : userList) {
//            if (nameUserapprove.length() < 1) {
//                nameUserapprove = "Waiting : " + user.getSsoId();
//            } else {
//                nameUserapprove = nameUserapprove + ", " + user.getSsoId();
//            }
//        }
//
//        if (nameUserapprove.isEmpty() || !nameUserapprove.contains("Waiting")) {
//            nameUserapprove = "Empty";
//        }
//        if (rule == 2 && not_approve2 == 1) {
//
//            ticketHeader.setApprovedName2(nameUserapprove);
//            ticketHeader.setApprovedName1("-");
//            ticketHeader.setApprovedPosition1("-");
//        } //ถ้าคนแรกหาไม่เจอ แต่คนที่2 หาไม่เจอ
//        else if (rule == 2 && not_approve2 == 2) {
//            if (not_approve2 == 2) {
//                System.out.println("check2:");
//                userList = userService.getMD();
//                nameUserapprove = "";
//                for (User user : userList) {
//                    if (nameUserapprove.length() < 1) {
//                        nameUserapprove = "Waiting : " + user.getSsoId();
//                    } else {
//                        nameUserapprove = nameUserapprove + ", " + user.getSsoId();
//                    }
//                }
//                System.out.println("userList: " + userList);
//                ticketHeader.setApprovedName1("-");
//                ticketHeader.setApprovedPosition1("-");
//                ticketHeader.setApprovedName2(nameUserapprove);
//
//            }
//        } else {
//            ticketHeader.setApprovedName1(nameUserapprove);
//        }
//
//    }
}
