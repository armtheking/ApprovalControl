package com.necl.core.report;

import com.necl.core.model.Position;
import com.necl.core.model.TicketHeader;
import com.necl.core.service.DivisionService;
import com.necl.core.service.PositionService;
import com.necl.core.service.SectionService;
import com.necl.core.service.TicketHeaderService;
import com.necl.training.model.TicketHTraining;
import com.necl.training.service.TicketHTrainingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles and retrieves download request
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    TicketHeaderService ticketHeaderService;

    @Autowired
    PositionService positionService;

    @Autowired
    DivisionService divisionService;

    @Autowired
    SectionService sectionService;

    @Autowired
    TicketHTrainingService ticketHTrainingService;

    /**
     * Handles and retrieves the download page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String getDownloadPage() {
        logger.debug("Received request to show download page");

        // Do your work here. Whatever you like
        // i.e call a custom service to do your business
        // Prepare a model to be used by the JSP page
        // This will resolve to /WEB-INF/jsp/downloadpage.jsp
        return "downloadpage";
    }

    @RequestMapping(value = "/download/pdf_HR", method = RequestMethod.GET)
    public ModelAndView reportHR(ModelAndView modelAndView, @RequestParam String id) {
        logger.debug("Received request to download PDF report");
        try {

            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            String ownerPositionName = positionService.findById(ticketHTraining.getTicketHeader().getApplicationPosition()).getPositionName();
            String ownerDivision = divisionService.findById(ticketHTraining.getTicketHeader().getDivisionCode()).getDivisionName();
            String ownerSection = sectionService.findById(ticketHTraining.getTicketHeader().getDivisionCode(), ticketHTraining.getTicketHeader().getSectionCode()).getSectionName();

            String firstApprovePositionName = null;

            Position find_position = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition1());

            if (find_position == null) {
                firstApprovePositionName = "-";
            } else {
                firstApprovePositionName = find_position.getPositionName();
            }
            String secondApprovePositionName = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition2()).getPositionName();

            ticketHTraining.getTicketHeader().setApplicationPosition(ownerPositionName);
            ticketHTraining.getTicketHeader().setDivisionCode(ownerDivision);
            ticketHTraining.getTicketHeader().setSectionCode(ownerSection);

            ticketHTraining.getTicketHeader().setApprovedPosition1(firstApprovePositionName);
            ticketHTraining.getTicketHeader().setApprovedPosition2(secondApprovePositionName);

            List<TicketHeader> ticketHeader = new ArrayList<>();
            ticketHeader.add(ticketHTraining.getTicketHeader());

            List<TicketHTraining> ticketHTrainingList = new ArrayList<TicketHTraining>();
            ticketHTrainingList.add(ticketHTraining);
            JRDataSource datasource_training = new JRBeanCollectionDataSource(ticketHTrainingList);
            if (id.contains("TRN")) {
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                parameterMap.put("datasource", datasource_training);
                modelAndView = new ModelAndView("trainingReport", parameterMap);
            } else {
                System.out.println("Report Fail");
            }
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/download/pdf_Finance", method = RequestMethod.GET)
    public ModelAndView reportFinance(ModelAndView modelAndView, @RequestParam String id) throws Exception {
        logger.debug("Received request to download PDF report");
        try {

            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            String ownerPositionName = positionService.findById(ticketHTraining.getTicketHeader().getApplicationPosition()).getPositionName();
            String ownerDivision = divisionService.findById(ticketHTraining.getTicketHeader().getDivisionCode()).getDivisionName();
            String ownerSection = sectionService.findById(ticketHTraining.getTicketHeader().getDivisionCode(), ticketHTraining.getTicketHeader().getSectionCode()).getSectionName();
            String firstApprovePositionName = null;

            Position find_position = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition1());

            if (find_position == null) {
                firstApprovePositionName = "-";
            } else {
                firstApprovePositionName = find_position.getPositionName();
            }

            String secondApprovePositionName = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition2()).getPositionName();

            ticketHTraining.getTicketHeader().setApplicationPosition(ownerPositionName);
            ticketHTraining.getTicketHeader().setDivisionCode(ownerDivision);
            ticketHTraining.getTicketHeader().setSectionCode(ownerSection);

            ticketHTraining.getTicketHeader().setApprovedPosition1(firstApprovePositionName);
            ticketHTraining.getTicketHeader().setApprovedPosition2(secondApprovePositionName);

            List<TicketHeader> ticketHeader = new ArrayList<>();
            ticketHeader.add(ticketHTraining.getTicketHeader());

            List<TicketHTraining> ticketHTrainingList = new ArrayList<TicketHTraining>();
            ticketHTrainingList.add(ticketHTraining);

            JRDataSource datasource_training = new JRBeanCollectionDataSource(ticketHTrainingList);
            if (id.contains("TRN")) {
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                parameterMap.put("datasource", datasource_training);
                modelAndView = new ModelAndView("trainingReport2", parameterMap);
            } else {
                System.out.println("Report Fail");
            }
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return null;
    }

       @RequestMapping(value = "/download/pdf_participant", method = RequestMethod.GET)
    public ModelAndView reportParticipant(ModelAndView modelAndView, @RequestParam String id) throws Exception {
        logger.debug("Received request to download PDF report");
        try {

            TicketHTraining ticketHTraining = ticketHTrainingService.findByTicketNo(id);

            String ownerPositionName = positionService.findById(ticketHTraining.getTicketHeader().getApplicationPosition()).getPositionName();
            String ownerDivision = divisionService.findById(ticketHTraining.getTicketHeader().getDivisionCode()).getDivisionName();
            String ownerSection = sectionService.findById(ticketHTraining.getTicketHeader().getDivisionCode(), ticketHTraining.getTicketHeader().getSectionCode()).getSectionName();
            String firstApprovePositionName = null;

            Position find_position = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition1());

            if (find_position == null) {
                firstApprovePositionName = "-";
            } else {
                firstApprovePositionName = find_position.getPositionName();
            }

            String secondApprovePositionName = positionService.findById(ticketHTraining.getTicketHeader().getApprovedPosition2()).getPositionName();

            ticketHTraining.getTicketHeader().setApplicationPosition(ownerPositionName);
            ticketHTraining.getTicketHeader().setDivisionCode(ownerDivision);
            ticketHTraining.getTicketHeader().setSectionCode(ownerSection);

            ticketHTraining.getTicketHeader().setApprovedPosition1(firstApprovePositionName);
            ticketHTraining.getTicketHeader().setApprovedPosition2(secondApprovePositionName);

            List<TicketHeader> ticketHeader = new ArrayList<>();
            ticketHeader.add(ticketHTraining.getTicketHeader());

            List<TicketHTraining> ticketHTrainingList = new ArrayList<TicketHTraining>();
            ticketHTrainingList.add(ticketHTraining);

            JRDataSource datasource_training = new JRBeanCollectionDataSource(ticketHTrainingList);
            if (id.contains("TRN")) {
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                parameterMap.put("datasource", datasource_training);
                modelAndView = new ModelAndView("training_participant", parameterMap);
            } else {
                System.out.println("Report Fail");
            }
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return null;
    }
    
    
    
    
    @RequestMapping(value = "/download/pdf_advance", method = RequestMethod.GET)
    public ModelAndView reportAdvance(ModelAndView modelAndView, @RequestParam String id) throws Exception {
        try{
        logger.debug("Received request to download PDF report");

        TicketHeader ticketHeaderById = ticketHeaderService.findById(id);

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

        List<TicketHeader> ticketHeader = new ArrayList<>();
        ticketHeader.add(ticketHeaderById);

        JRDataSource datasource = new JRBeanCollectionDataSource(ticketHeader);

        // In order to use Spring's built-in Jasper support, 
        // We are required to pass our datasource as a map parameter
        // parameterMap is the Model of our application
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("datasource", datasource);

        // pdfReport is the View of our application
        // This is declared inside the /WEB-INF/jasper-views.xml
        if (id.contains("ADV")) {
            modelAndView = new ModelAndView("advanceReport", parameterMap);
        } else {
            System.out.println("Report Fail");
        }

        // Return the View and the Model combined
        return modelAndView;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/download/pdf_advanceC", method = RequestMethod.GET)
    public ModelAndView reportAdvanceClear(ModelAndView modelAndView, @RequestParam String id) {
        try {
            logger.debug("Received request to download PDF report");

            TicketHeader ticketHeaderById = ticketHeaderService.findById(id);

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

            List<TicketHeader> ticketHeader = new ArrayList<>();
            ticketHeader.add(ticketHeaderById);

            JRDataSource datasource = new JRBeanCollectionDataSource(ticketHeader);

        // In order to use Spring's built-in Jasper support, 
            // We are required to pass our datasource as a map parameter
            // parameterMap is the Model of our application
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("datasource", datasource);

        // pdfReport is the View of our application
            // This is declared inside the /WEB-INF/jasper-views.xml
            if (id.contains("ADV")) {
                modelAndView = new ModelAndView("advanceClearReport", parameterMap);
            } else {
                System.out.println("Report Fail");
            }

            // Return the View and the Model combined
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/download/xls", method = RequestMethod.GET)
    public ModelAndView doSalesReportXLS(ModelAndView modelAndView) {
        logger.debug("Received request to download Excel report");

        // Retrieve our data from a custom data provider
        // Our data comes from a DAO layer
        // Assign the datasource to an instance of JRDataSource
        // JRDataSource is the datasource that Jasper understands
        // This is basically a wrapper to Java's collection classes
//		JRDataSource datasource  = dataprovider.getDataSource();
        // In order to use Spring's built-in Jasper support, 
        // We are required to pass our datasource as a map parameter
        // parameterMap is the Model of our application
//		Map<String,Object> parameterMap = new HashMap<String,Object>();
//		parameterMap.put("datasource", datasource);
//		
//		// xlsReport is the View of our application
//		// This is declared inside the /WEB-INF/jasper-views.xml
//		modelAndView = new ModelAndView("xlsReport", parameterMap);
        // Return the View and the Model combined
        return modelAndView;
    }

    /**
     * Retrieves the download file in XLS format
     *
     * @return
     */
    @RequestMapping(value = "/download/pdf", method = RequestMethod.GET)
    public ModelAndView doSalesReportPDF(ModelAndView modelAndView, @RequestParam String id) throws Exception {
        logger.debug("Received request to download PDF report");

        TicketHeader ticketHeaderById = ticketHeaderService.findById(id);

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

        List<TicketHeader> ticketHeader = new ArrayList<>();
        ticketHeader.add(ticketHeaderById);

        JRDataSource datasource = new JRBeanCollectionDataSource(ticketHeader);

        // In order to use Spring's built-in Jasper support, 
        // We are required to pass our datasource as a map parameter
        // parameterMap is the Model of our application
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("datasource", datasource);

        // pdfReport is the View of our application
        // This is declared inside the /WEB-INF/jasper-views.xml
        if (id.contains("ENT")) {
            modelAndView = new ModelAndView("entertainReport", parameterMap);
        } else {
            System.out.println("Report Fail");
        }

        // Return the View and the Model combined
        return modelAndView;
    }

    @RequestMapping(value = "/download/pdfEntertainC", method = RequestMethod.GET)
    public ModelAndView doEntertainClearReportPDF(ModelAndView modelAndView, @RequestParam String id) throws Exception {
        logger.debug("Received request to download PDF report");

        TicketHeader ticketHeaderById = ticketHeaderService.findById(id);

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

        List<TicketHeader> ticketHeader = new ArrayList<>();
        ticketHeader.add(ticketHeaderById);

        JRDataSource datasource = new JRBeanCollectionDataSource(ticketHeader);

        // In order to use Spring's built-in Jasper support, 
        // We are required to pass our datasource as a map parameter
        // parameterMap is the Model of our application
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("datasource", datasource);

        // pdfReport is the View of our application
        // This is declared inside the /WEB-INF/jasper-views.xml
        if (id.contains("ENT")) {
            modelAndView = new ModelAndView("entertainClearReport", parameterMap);
        } else {
            System.out.println("Report Fail");
        }

        // Return the View and the Model combined
        return modelAndView;
    }
}
