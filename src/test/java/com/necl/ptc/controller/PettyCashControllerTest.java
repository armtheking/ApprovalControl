/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.ptc.controller;

import com.necl.core.controller.CreateTicketController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
/**
 *
 * @author C13.207
 */
public class PettyCashControllerTest {
//    
//    @InjectMocks
//    private CreateTicketController pettyCashController;
//    
//    MockMvc mockMvc;
//    
//    public PettyCashControllerTest() {
//    }
//    
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/view/");
//        viewResolver.setSuffix(".jsp");
//        
//        mockMvc = MockMvcBuilders.standaloneSetup(pettyCashController).setViewResolvers(viewResolver).build();
//    }
//
//
//    @Test
//     public void testIndexMethodPettyCash() throws Exception{
//        mockMvc.perform(get("/pettycash")).andDo(print());
//    }
}
