/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.controller;

import com.necl.core.model.History;
import com.necl.core.model.HistoryNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.service.HistoryService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author C13.272
 */
@Controller
@RequestMapping(value = "/showRevise")
public class ReviseController {

    @Autowired
    HistoryService historyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAdvancePage(@RequestParam String id) {

        try {
            ModelAndView model = new ModelAndView();

            List<History> historyList = new ArrayList<>();
            historyList = historyService.findByTicketNo(id);
            DecimalFormat numFormat;
            numFormat = new DecimalFormat("#,##0.00");
            List<HistoryNumber> number2 = new ArrayList<>();

            for (int i = 0; i < historyList.size(); i++) {
                HistoryNumber number = new HistoryNumber();

                number.setTicketRev(historyList.get(i).getTicketRev());
                number.setDate(historyList.get(i).getDate());
                number.setApprovedName1(historyList.get(i).getApprovedName1());
                number.setApprovedRemark1(historyList.get(i).getApprovedRemark1());
                number.setApprovedName2(historyList.get(i).getApprovedName2());
                number.setApprovedRemark2(historyList.get(i).getApprovedRemark2());
                number.setReqTotalAmt(numFormat.format(historyList.get(i).getReqTotalAmt()));
                number2.add(number);
            }

            model.addObject("historyList", number2);
            model.setViewName("showrevise");
            return model;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
