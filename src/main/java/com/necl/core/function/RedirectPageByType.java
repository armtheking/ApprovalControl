package com.necl.core.function;

import org.apache.log4j.Logger;

public class RedirectPageByType {

    private static final Logger LOGGER = Logger.getLogger(RedirectPageByType.class);

    // find page each application type current 4 type 15/12/2015
    
    public static String getPageByType(String type) {
        String redirectPage = "";

        if (type.contains("ENT")) {
            redirectPage = "redirect:/entertain";
        } else if (type.contains("ADV")) {
            redirectPage = "redirect:/advance";
        } else if (type.contains("PTC")) {
            redirectPage = "redirect:/pettycash";
        } else if (type.contains("TRN")) {
            redirectPage = "redirect:/training";
        } else {
            redirectPage = "redirect:/home";
            LOGGER.info("redirect page : !" + redirectPage);
        }
        return redirectPage;
    }
}
