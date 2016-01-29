package com.necl.core.service;

import com.necl.core.model.ConfigSystem;
import com.necl.core.model.Mail;
import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("mailService")
@Transactional
public class SendMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigSystemService configSystemService;

    private void sendEmail(Mail mail) throws Exception {

        // reads form input
        final String emailTo = mail.getMailTo(); // คนที่มีสิทธิ์ approve

        InternetAddress[] iAdressArray = InternetAddress.parse(emailTo);

        // for logging
//        System.out.println("emailTo: " + emailTo);
//        System.out.println("subject: " + subject);
//        System.out.println("message: " + message);
        mailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(
                        mimeMessage, true, "UTF-8");
                messageHelper.setTo(iAdressArray);
                messageHelper.setSubject(mail.getMailSubject());
                messageHelper.setText(mail.getMailMessage(), true);
                messageHelper.setFrom(mail.getMailFrom());
            }

        });
    }

    public void sendMailUserApprove(TicketHeader ticketHeader) throws Exception {

        Mail mail = new Mail();
        String emailTo = convertEmailAddress(ticketHeader.getTicketNo());
        Map<String, Object> modelMap = new HashMap<>();
        String message;

        String typeName="";
        if(ticketHeader.getTicketType().equals("ENT")){
            typeName="Entertain";
        }
        else if(ticketHeader.getTicketType().equals("TRN")){
            typeName="Training";
        }
         else if(ticketHeader.getTicketType().equals("ADV")){
            typeName="Advance";
        }
         else if(ticketHeader.getTicketType().equals("PTC")){
            typeName="Petty Cash";
        }
        
        
        //set variable show in vm file
        modelMap.put("ticketNo", ticketHeader.getTicketNo());
        modelMap.put("ticketType", typeName);
        modelMap.put("applicationName", ticketHeader.getApplicationName());

        //set detail Mail Object
        mail.setMailFrom(mail.getMailFrom());
        mail.setMailTo(emailTo);
        mail.setMailSubject("Request for Approval Control. ( "+ticketHeader.getTicketNo()+" )");
        mail.setTemplateName("/mail/approve.vm");

        //set body message
        message = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, mail.getTemplateName(), "UTF-8", modelMap);

        mail.setMailMessage(message);

        sendEmail(mail);
        //return emailto;
    }

    public void sendMailToOwnerTicket(TicketHeader ticketHeader) throws Exception {

        Mail mail = new Mail();
        Map<String, Object> modelMap = new HashMap<>();

        /*
         get Config assign email from DB
         */
        String assign = configSystemService.findByKey("EMAIL").getConfigText();

        User user = userService.findBySso(ticketHeader.getApplicationName());

        String emailTo = user.getEmail() + assign;

        //set variable show in vm file
        modelMap.put("ticketNo", ticketHeader.getTicketNo());
        modelMap.put("ticketType", ticketHeader.getTicketType());
        modelMap.put("applicationName", ticketHeader.getApplicationName());

        // set detail Mail Object
        mail.setMailFrom(mail.getMailFrom());
        mail.setMailTo(emailTo);
        
        // 29/12/58
        if (ticketHeader.getTicketFinished().equals("R")) {
            mail.setMailSubject("Your request was rejected. ( "+ticketHeader.getTicketNo()+" )");
            mail.setTemplateName("/mail/reject.vm");
        } else {
            mail.setMailSubject("Your request already approved. ( "+ticketHeader.getTicketNo()+" )");
            mail.setTemplateName("/mail/print.vm");
        }

       

        //set body message
        String message = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, mail.getTemplateName(), "UTF-8", modelMap);

        mail.setMailMessage(message);

        sendEmail(mail);
    }

//    Convert multiple email address
    private String convertEmailAddress(String ticketNo) throws Exception {
        String assign = configSystemService.findByKey("EMAIL").getConfigText();
        List<User> userList = userService.findMailUserApprove(ticketNo);
        String emailTo = "";

        for (User user : userList) {
            if (emailTo.length() < 1) {
                emailTo = user.getEmail() + assign;
            } else {
                emailTo = emailTo + "," + user.getEmail() + assign;
            }
        }

        return emailTo;
    }

}

// code attachment
//                // determines if there is an upload file, attach it to the e-mail
//                String attachName = attachFile.getOriginalFilename();
//                if (!attachFile.equals("")) {
//
//                    messageHelper.addAttachment(attachName, new InputStreamSource() {
//
//                        @Override
//                        public InputStream getInputStream() throws IOException {
//                            return attachFile.getInputStream();
//                        }
//                    });
//                }
