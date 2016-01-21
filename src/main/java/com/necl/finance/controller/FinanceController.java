package com.necl.finance.controller;

import com.necl.core.model.TicketHeader;
import com.necl.core.service.TicketHeaderService;
import com.necl.login.controller.HomeController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/finance")
public class FinanceController {

    static private Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    TicketHeaderService ticketHeaderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws Exception {

        LOGGER.debug("Home Page !");

        return "redirect:/home";

    }

    @RequestMapping(value = "/entertain", method = RequestMethod.GET)
    public String fnEntertain(Model model) throws Exception {
        System.out.println("check1");
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findByType("ENT"));
        System.out.println("check2");
        LOGGER.debug("fn_Entertain Page !");

        return "finance/fn_entertain";

    }
    
     @RequestMapping(value = "/advance", method = RequestMethod.GET)
    public String fnAdvance(Model model) throws Exception {
        System.out.println("check1");
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findByType("ADV"));
        System.out.println("check2");
        LOGGER.debug("fn_Advance Page !");

        return "finance/fn_advance";

    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showTicket(@RequestParam String id) {
        try {
            LOGGER.info("show is exeuted!");
            ModelAndView model = new ModelAndView();
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            model.addObject("ticketHeader", ticketHeader);
            if (ticketHeader == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("finance/preview_check");
            }

            return model;
        } catch (Exception e) {
            LOGGER.info("show Exception!");
        }

        return null;
    }
    
     @RequestMapping(value = "/showAdvance", method = RequestMethod.GET)
    public ModelAndView showAdvance(@RequestParam String id) {
        try {
            LOGGER.info("show is exeuted!");
            ModelAndView model = new ModelAndView();
            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            model.addObject("ticketHeader", ticketHeader);
            if (ticketHeader == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("finance/check_advance");
            }

            return model;
        } catch (Exception e) {
            LOGGER.info("show Exception!");
        }

        return null;
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ModelAndView financeApprove(@ModelAttribute("ticketHeader") TicketHeader ticketHeader) {
        try {
            String referanceTicketNo = "";

            LOGGER.info("find data current: No. " + ticketHeader.getTicketNo());
            LOGGER.info(" Receiver : " + ticketHeader.getReceiverBy());
            TicketHeader ticketHeaderDataCurrent = ticketHeaderService.findById(ticketHeader.getTicketNo());

            LOGGER.info("set data approve");
            ticketHeaderDataCurrent.setReceiverBy(ticketHeader.getReceiverBy());
            ticketHeaderDataCurrent.setPaidRemark(ticketHeader.getPaidRemark());
            ticketHeaderDataCurrent.setPayment(ticketHeader.getPayment());
            ticketHeaderDataCurrent.setPaidBy(HomeController.getPrincipal());
            ticketHeaderDataCurrent.setPaidStatus(true);

            if (ticketHeader.getTicketNo().contains("-C")) {
                ticketHeaderDataCurrent.setTicketFinished("F");
                referanceTicketNo = ticketHeaderDataCurrent.getRefTicketNo();
            } else {
                ticketHeaderDataCurrent.setTicketFinished("W");
            }

            LOGGER.info("set data success");
            ticketHeaderService.update(ticketHeaderDataCurrent);

            // Update Status referance ticket  finish (F)
            if (ticketHeader.getTicketNo().contains("-C")) {
                TicketHeader referanceTicket = ticketHeaderService.findById(referanceTicketNo);
                referanceTicket.setTicketFinished("F");
                ticketHeaderService.update(referanceTicket);
                LOGGER.info("update referance ticket success");
            }

            LOGGER.info("save data success");

            if(ticketHeader.getTicketNo().contains("ENT")){
                return new ModelAndView("redirect:/finance/entertain");
            }
            else if(ticketHeader.getTicketNo().contains("ADV")){
                return new ModelAndView("redirect:/finance/advance");
            }
            
        } catch (Exception e) {
            LOGGER.info("save edit Exception!");
            e.printStackTrace();
        }
        return null;
    }
}
