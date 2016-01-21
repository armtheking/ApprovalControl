package com.necl.training.controller;

import com.necl.core.model.TicketHeader;
import com.necl.core.service.TicketHeaderService;
import com.necl.training.model.DivisionSumBudget;
import com.necl.training.model.DivisionSumBudgetList;
import com.necl.training.model.DivisionSumBudgetNumber;
import com.necl.training.model.TicketDTraining;
import com.necl.training.model.TicketDTrainingNumber;
import com.necl.training.model.TicketHTraining;
import com.necl.training.service.DivisionBudgetService;
import com.necl.training.service.TicketHTrainingService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/training")
public class TrainingPageController {

    @Autowired
    TicketHTrainingService ticketHTrainingService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    DivisionBudgetService divisionBudgetService;

    static private Logger LOGGER = LoggerFactory.getLogger(TrainingCreateTicketController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
//        ModelAndView model = new ModelAndView();
        if (!model.containsAttribute("ticketHTraining")) {
            model.addAttribute("ticketHTraining", new TicketHTraining());
        }
        LOGGER.info("first page index ");
        System.out.println("test");
        return "training/training";

    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showTicket(@RequestParam String id) {
        try {
            LOGGER.info("show is exeuted!");
            ModelAndView model = new ModelAndView();
            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            String number_sumAmount;
            String number_costPerHead;
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            number_sumAmount = numFormat.format(ticketHTraining.getTicketHeader().getReqTotalAmt());
            number_costPerHead = numFormat.format(ticketHTraining.getCostPerHead());

            List<TicketDTrainingNumber> number2 = new ArrayList<>();

            for (int i = 0; i < ticketHTraining.getTicketHeader().getTicketDTraining().size(); i++) {
                TicketDTrainingNumber number = new TicketDTrainingNumber();
                number.setItem(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getItem());
                number.setBudgetDetail(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getBudgetDetail());
                number.setAmount(numFormat.format(ticketHTraining.getTicketHeader().getTicketDTraining().get(i).getAmount()));
                number2.add(number);
            }

            model.addObject("ticketHTraining", ticketHTraining);
            model.addObject("number_sumAmount", number_sumAmount);
            model.addObject("number_costPerHead", number_costPerHead);
            model.addObject("ticketDTraining", number2);

            if (ticketHTraining == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("showtraining");
            }

            return model;
        } catch (Exception e) {
            LOGGER.info("show Exception!");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public ModelAndView printTicket(@RequestParam String id) {
        try {
            LOGGER.info("show is exeuted!");
            ModelAndView model = new ModelAndView();
            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            ticketHTraining.getTicketHeader().setTicketFinished("F");
            ticketHeaderService.save(ticketHTraining.getTicketHeader());
            model.addObject("ticketHTraining", ticketHTraining);
            if (ticketHTraining == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("showtraining");
            }

            return model;
        } catch (Exception e) {
            LOGGER.info("show Exception!");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = {"/toEdit"}, method = RequestMethod.GET)
    public ModelAndView toEditTicket(@RequestParam String id) {
        try {
            LOGGER.info("edit is exeuted!");

//            TicketHeader ticketHeader = ticketHeaderService.findById(id);
//            TicketHTraining ticketHTraining = new TicketHTraining();
//            ticketHTraining.setTicketHeader(ticketHeader);
            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            List<TicketDTraining> ticketDTraining = new ArrayList<>();
            for (int i = 6; i < ticketHTraining.getTicketHeader().getTicketDTraining().size(); i++) {
                ticketDTraining.add(ticketHTraining.getTicketHeader().getTicketDTraining().get(i));
            }

            ModelAndView model = new ModelAndView();
            model.addObject("ticketHTraining", ticketHTraining);
            model.addObject("ticketDTraining", ticketDTraining);
            model.addObject("trainingParticipant", ticketHTraining.getTicketHeader().getTrainingParticipant());
//            model.addObject("participant", ticketHeader.getTrainingParticipant());
            model.setViewName("training/trainingedit");
            return model;
        } catch (Exception e) {
            LOGGER.info("edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/viewBudget"}, method = RequestMethod.GET)
    public ModelAndView viewBudget() {

        try {
            String user = getPrincipal();

            List<DivisionSumBudget> div = new ArrayList<>();
            div = divisionBudgetService.sumBudget();

            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");

            List<DivisionSumBudgetNumber> number2 = new ArrayList<>();

            for (int i = 0; i < div.size(); i++) {
                DivisionSumBudgetNumber number = new DivisionSumBudgetNumber();
                number.setDivisionCode(div.get(i).getDivisionCode());
                number.setDivisionName(div.get(i).getDivisionName());
                number.setMaxBudget(numFormat.format(div.get(i).getMaxBudget()));
                number.setSumBudget(numFormat.format(div.get(i).getSumBudget()));
                number.setBalance(numFormat.format(div.get(i).getBalance()));
                number2.add(number);
            }
            ModelAndView model = new ModelAndView();

            model.setViewName("training/viewbudget");
            model.addObject("budget", number2);
            model.addObject("user", user);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/toEditBudget"}, method = RequestMethod.POST)
    public ModelAndView toEditBudget() {
        try {
            String user = getPrincipal();
            ModelAndView model = new ModelAndView();
            List<DivisionSumBudget> div = new ArrayList<>();
            div = divisionBudgetService.sumBudget();

            DivisionSumBudgetList divisionSumBudgetList = new DivisionSumBudgetList();

            model.addObject("budget", div);

            model.addObject("divisionSumBudgetList", divisionSumBudgetList);

            model.setViewName("training/trainingeditbudget");
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
