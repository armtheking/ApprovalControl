package com.necl.core.controller;

import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.login.controller.HomeController;
import java.util.Calendar;
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
    public String index(Model model) throws Exception {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String sso_id = HomeController.getPrincipal();

        String division = userService.findBySso(sso_id).getDivisionCode();
        
        model.addAttribute("active", "viewdata");
        model.addAttribute("ticketHeaderView", ticketHeaderService.findMonthYear(month, year, division));

        LOGGER.info("viewdata page index ");

        return "viewdata";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam int month, @RequestParam int year) throws Exception {
        
        String sso_id = HomeController.getPrincipal();

        String division = userService.findBySso(sso_id).getDivisionCode();
        
        model.addAttribute("active", "viewdata");
        model.addAttribute("ticketHeaderView", ticketHeaderService.findMonthYear(month, year, division));

        LOGGER.info("viewdata page index ");

        return "viewdata";
    }
}
