package com.necl.finance.controller;

import com.necl.core.model.Position;
import com.necl.core.model.TicketDetailNumber;
import com.necl.core.model.TicketHeader;
import com.necl.core.service.DivisionService;
import com.necl.core.service.PositionService;
import com.necl.core.service.SectionService;
import com.necl.core.service.TicketHeaderService;
import com.necl.login.controller.HomeController;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

    @Autowired
    PositionService positionService;

    @Autowired
    DivisionService divisionService;

    @Autowired
    SectionService sectionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws Exception {

        LOGGER.debug("Home Page !");

        return "redirect:/home";

    }

    @RequestMapping(value = "/entertain", method = RequestMethod.GET)
    public String fnEntertain(Model model) throws Exception {
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findByType("ENT"));
        model.addAttribute("THANIYA", ticketHeaderService.findByTypeArea("ENT", "THANIYA"));
        model.addAttribute("BLC", ticketHeaderService.findByTypeArea("ENT", "BLC"));
        model.addAttribute("NLC", ticketHeaderService.findByTypeArea("ENT", "NLC"));
        model.addAttribute("AIRPORT", ticketHeaderService.findByTypeArea("ENT", "AIRPORT"));
        model.addAttribute("NCC", ticketHeaderService.findByTypeArea("ENT", "NCC"));
        model.addAttribute("LAEMCHABANG", ticketHeaderService.findByTypeArea("ENT", "LAEMCHABANG"));
        model.addAttribute("KORAT", ticketHeaderService.findByTypeArea("ENT", "KORAT"));

        LOGGER.debug("fn_Entertain Page !");

        return "finance/fn_entertain";

    }

    @RequestMapping(value = "/advance", method = RequestMethod.GET)
    public String fnAdvance(Model model) throws Exception {
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findByType("ADV"));
        model.addAttribute("THANIYA", ticketHeaderService.findByTypeArea("ADV", "THANIYA"));
        model.addAttribute("BLC", ticketHeaderService.findByTypeArea("ADV", "BLC"));
        model.addAttribute("NLC", ticketHeaderService.findByTypeArea("ADV", "NLC"));
        model.addAttribute("AIRPORT", ticketHeaderService.findByTypeArea("ADV", "AIRPORT"));
        model.addAttribute("NCC", ticketHeaderService.findByTypeArea("ADV", "NCC"));
        model.addAttribute("LAEMCHABANG", ticketHeaderService.findByTypeArea("ADV", "LAEMCHABANG"));
        model.addAttribute("KORAT", ticketHeaderService.findByTypeArea("ADV", "KORAT"));
        LOGGER.debug("fn_Advance Page !");

        return "finance/fn_advance";

    }

    @RequestMapping(value = "/pettycash", method = RequestMethod.GET)
    public String fnPettycash(Model model) throws Exception {
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findByType("PTC"));
        model.addAttribute("THANIYA", ticketHeaderService.findByTypeArea("PTC", "THANIYA"));
        model.addAttribute("BLC", ticketHeaderService.findByTypeArea("PTC", "BLC"));
        model.addAttribute("NLC", ticketHeaderService.findByTypeArea("PTC", "NLC"));
        model.addAttribute("AIRPORT", ticketHeaderService.findByTypeArea("PTC", "AIRPORT"));
        model.addAttribute("NCC", ticketHeaderService.findByTypeArea("PTC", "NCC"));
        model.addAttribute("LAEMCHABANG", ticketHeaderService.findByTypeArea("PTC", "LAEMCHABANG"));
        model.addAttribute("KORAT", ticketHeaderService.findByTypeArea("PTC", "KORAT"));
        LOGGER.debug("fn_Pettycash Page !");

        return "finance/fn_pettycash";

    }

    @RequestMapping(value = "/viewData", method = RequestMethod.GET)
    public String fnViewData(Model model) throws Exception {
        model.addAttribute("ticketHeaderListFinance", ticketHeaderService.findFinish());
        model.addAttribute("THANIYA", ticketHeaderService.findFinish("THANIYA"));
        model.addAttribute("BLC", ticketHeaderService.findFinish("BLC"));
        model.addAttribute("NLC", ticketHeaderService.findFinish("NLC"));
        model.addAttribute("AIRPORT", ticketHeaderService.findFinish("AIRPORT"));
        model.addAttribute("NCC", ticketHeaderService.findFinish("NCC"));
        model.addAttribute("LAEMCHABANG", ticketHeaderService.findFinish("LAEMCHABANG"));
        model.addAttribute("KORAT", ticketHeaderService.findFinish("KORAT"));
        return "finance/fn_viewdata";

    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String printFinance(Model model) throws Exception {

        return "finance/fn_print";

    }

    @RequestMapping(value = "/printReport", method = RequestMethod.POST)
    public ModelAndView printReportFinance(@ModelAttribute(value = "ticketHeader") TicketHeader ticketHeader) throws Exception {

        try {
            ModelAndView model = new ModelAndView();

            String str = ticketHeader.getTicketNo().trim();
            TicketHeader ticketHeaderById = ticketHeaderService.findById(str);
            if (ticketHeaderById != null && (ticketHeaderById.getTicketFinished().equals("P") || ticketHeaderById.getTicketFinished().equals("F"))) {

                String ownerPositionName = positionService.findById(ticketHeaderById.getApplicationPosition()).getPositionName();
                String ownerDivision = divisionService.findById(ticketHeaderById.getDivisionCode()).getDivisionName();
                String ownerSection = sectionService.findById(ticketHeaderById.getDivisionCode(), ticketHeaderById.getSectionCode()).getSectionName();

                String firstApprovePositionName = null;

                Position find_position = positionService.findById(ticketHeaderById.getApprovedPosition1());

                if (find_position == null) {
                    firstApprovePositionName = "-";
                } else {
                    firstApprovePositionName = find_position.getPositionName();
                }
                String secondApprovePositionName = positionService.findById(ticketHeaderById.getApprovedPosition2()).getPositionName();

                ticketHeaderById.setApplicationPosition(ownerPositionName);
                ticketHeaderById.setDivisionCode(ownerDivision);
                ticketHeaderById.setSectionCode(ownerSection);

                ticketHeaderById.setApprovedPosition1(firstApprovePositionName);
                ticketHeaderById.setApprovedPosition2(secondApprovePositionName);

                List<TicketHeader> ticketHeader2 = new ArrayList<>();
                ticketHeader2.add(ticketHeaderById);

                JRDataSource datasource = new JRBeanCollectionDataSource(ticketHeader2);

                // In order to use Spring's built-in Jasper support, 
                // We are required to pass our datasource as a map parameter
                // parameterMap is the Model of our application
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                parameterMap.put("datasource", datasource);

                // pdfReport is the View of our application
                // This is declared inside the /WEB-INF/jasper-views.xml
                if (ticketHeader.getTicketNo().contains("ADV") && ticketHeader.getTicketNo().contains("-C")) {
                    model = new ModelAndView("advanceClearReport", parameterMap);
                } else if (ticketHeader.getTicketNo().contains("ADV") && !ticketHeader.getTicketNo().contains("-C")) {
                    model = new ModelAndView("advanceReport", parameterMap);
                } else if (ticketHeader.getTicketNo().contains("ENT") && ticketHeader.getTicketNo().contains("-C")) {
                    model = new ModelAndView("entertainClearReport", parameterMap);
                } else if (ticketHeader.getTicketNo().contains("ENT") && !ticketHeader.getTicketNo().contains("-C")) {
                    model = new ModelAndView("entertainReport", parameterMap);
                } else if (ticketHeader.getTicketNo().contains("PTC")) {
                    model = new ModelAndView("pettycashReport", parameterMap);
                } else {
                    System.out.println("Report Fail");
                }

                // Return the View and the Model combined
                return model;
            } else {
                model.setViewName("finance/not_found");
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

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
            model.addObject("number_sumAmount", number_sumAmount);
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

    @RequestMapping(value = "/showPettycash", method = RequestMethod.GET)
    public ModelAndView showPettycash(@RequestParam String id) {
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
                number.setReceiptNo(ticketHeader.getTicketdetail().get(i).getDescription());
                number.setAmount(numFormat.format(ticketHeader.getTicketdetail().get(i).getAmount()));
                number2.add(number);
            }

            model.addObject("ticketDetail", number2);
            model.addObject("number_sumAmount", number_sumAmount);

            model.addObject("ticketHeader", ticketHeader);
            if (ticketHeader == null) {
                model.addObject("search", "No results found for " + id);
                model.setViewName("redirect:/home");
            } else {
                model.setViewName("finance/check_pettycash");
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
            ticketHeaderDataCurrent.setPaidDate(Calendar.getInstance());
            ticketHeaderDataCurrent.setPaidStatus(true);

            if (ticketHeader.getTicketNo().contains("-C") || ticketHeader.getTicketNo().contains("PTC")) {
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

            if (ticketHeader.getTicketNo().contains("ENT")) {
                return new ModelAndView("redirect:/finance/entertain");
            } else if (ticketHeader.getTicketNo().contains("ADV")) {
                return new ModelAndView("redirect:/finance/advance");
            } else if (ticketHeader.getTicketNo().contains("PTC")) {
                return new ModelAndView("redirect:/finance/pettycash");
            }

        } catch (Exception e) {
            LOGGER.info("save edit Exception!");
            e.printStackTrace();
        }
        return null;
    }

}
