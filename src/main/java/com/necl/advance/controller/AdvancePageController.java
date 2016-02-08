package com.necl.advance.controller;

import com.necl.core.model.FinanceChargeCode;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.FinChargeCodeService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserService;
import com.necl.entertain.controller.EntertainController;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

@Controller
@RequestMapping(value = "/advance")
public class AdvancePageController {

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
        model.addAttribute("active", "advance");

        return "advance/advance";
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

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showTicket(@RequestParam String id) {
        try {
            System.out.println("xxx: "+id);
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
                number2.add(number);
            }
            
            
            
             model.addObject("ticketDetail", number2);
            model.addObject("ticketHeader", ticketHeader);
            model.addObject("number_sumAmount", number_sumAmount);

            
            
            
            
            if (ticketHeader == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("showadvance");
            }

            return model;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public ModelAndView editTicket(@RequestParam String id) {
        try {

            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            ModelAndView model = new ModelAndView();
            model.addObject("ticketHeader", ticketHeader);

            model.setViewName("advance/advanceedit");
            return model;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/edit_clear"}, method = RequestMethod.GET)
    public ModelAndView editTicketClear(@RequestParam String id) {
        try {

            TicketHeader ticketHeader = ticketHeaderService.findById(id);

            ModelAndView model = new ModelAndView();
            model.addObject("ticketHeader", ticketHeader);

            model.setViewName("enadvancetertain/advanceedit_clear");
            return model;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

}
