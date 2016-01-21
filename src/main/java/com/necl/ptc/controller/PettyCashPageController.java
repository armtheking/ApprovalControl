package com.necl.ptc.controller;

import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/pettycash")
public class PettyCashPageController {
    @Autowired
    FinChargeCodeService finChargeCodeService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAdvancePage(Model model) {
        //model.addAttribute("user", getPrincipal());
        if (!model.containsAttribute("ticketHeader")) {
            model.addAttribute("ticketHeader", new TicketHeader());
        }
        model.addAttribute("pettycash", "pettycash");

        return "pettycash/pettycash";
    }

    @ModelAttribute
    public void addAttributes(Model model) throws Exception {
        String idBranchUser = findBranchUser();
        List<FinanceChargeCode> chargeCodeList = finChargeCodeService.findChargeCodeAsType(idBranchUser, "ADV");

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
