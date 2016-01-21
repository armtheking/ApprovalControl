/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.necl.core.model.TicketHeader;
import com.necl.core.service.SendMailService;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.serviceImpl.TicketHeaderServiceImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author C13.207
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:**/applicationContext.xml",
//                               "classpath:**/mvc-dispatcher-servlet.xml", 
//                               "classpath:**/spring-database.xml"})
public class SendMailServiceTest{
//
//    @Autowired
//    private SendMailService sendMailService;
//
//    @Autowired
//    private TicketHeaderService ticketHeaderService;
//
//    @Autowired
//    private TicketHeader ticketHeader;
//
//    @Before
//    public void setUp() throws Exception {
//        ticketHeader = ticketHeaderService.findById("ENT1511/0024");
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // 
    @Test
    public void testSendMailtoOwner() throws Exception {
        assertEquals(1, 1);
    }
}
