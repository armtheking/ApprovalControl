/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.entertain.controller;

import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
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

}
