/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.login.controller;

import com.necl.core.model.User;
import com.necl.core.model.UserProfile;
import com.necl.core.service.TicketHeaderService;
import com.necl.core.service.UserProfileService;
import com.necl.core.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
public class HomeController {

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserService userService;

    @Autowired
    TicketHeaderService ticketHeaderService;

    private static final Logger LOGGER = Logger.getLogger(HomeController.class);

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) throws Exception {
        /*
         use stored procuder show table first
         @Param1 Position User login 
         @Param2 Division User login
         @Param3 Username login (Login with Sso_id)
         */
        model.addAttribute("ticketHeaderList", ticketHeaderService.storedShowApproves(userService.findBySso(HomeController.getPrincipal())));

        /*
         use stored procuder show table second
         @Param1 Username login
         */
        model.addAttribute("ticketHeaderListBelowTable", ticketHeaderService.storedShowTicket(userService.findBySso(HomeController.getPrincipal()).getSsoId()));
        LOGGER.debug("Home Page !");
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/dba", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "dba";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
//        return "accessDenied";
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/loginFail", method = RequestMethod.GET)
    public String loginFailPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?error";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            System.out.println("There are errors");
            return "newuser";
        }

        userService.save(user);
        System.out.println("First Name : " + user.getFirstName());
        System.out.println("Last Name : " + user.getLastName());
        System.out.println("SSO ID : " + user.getSsoId());
        System.out.println("Password : " + user.getPassword());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Checking UsrProfiles....");

        if (user.getUserProfiles() != null) {
            for (UserProfile profile : user.getUserProfiles()) {
                System.out.println("Profile : " + profile.getType());
            }
        }
        model.addAttribute("success", "User " + user.getFirstName() + " has been registered successfully");
        return "registrationsuccess";
    }

    public static String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(ModelMap model) {
        System.out.println("ffff");
        User user = new User();
        model.addAttribute("user", user);
        return "resetpassword";
    }

    @RequestMapping(value = "/resetPassword_success", method = RequestMethod.POST)
    public ModelAndView resetPassword_success(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirm_newPassword) {
        ModelAndView model = new ModelAndView();
        try {
            String reset = userService.resetPassword(oldPassword, newPassword, confirm_newPassword);
            if(reset.equals("Password was incorrect!")){
                System.out.println("Password was incorrect!");
                model.addObject("message", "Password was incorrect!");
            }
            else if(reset.equals("Your new password and confirm password do not match!")){
                 model.addObject("message", "Your new password and confirm password do not match!");
            }
            else if(reset.equals("Your password has been reset successfully!")){
                 model.addObject("message", "Your password has been reset successfully!");
            }
            else{
                 model.addObject("message", "ERROR!");
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        
       model.setViewName("resetpassword");
        return model;
    }

}
