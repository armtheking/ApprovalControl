package com.necl.core.function;

import com.necl.core.model.TicketHeader;
import com.necl.core.model.User;
import com.necl.core.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class SetNameWaitingApprove {

    @Autowired
    private UserService userService;

    public String setNameWaitingApprove1(TicketHeader ticketHeader) throws Exception {
        List<User> userList = userService.findMailUserApprove(ticketHeader.getTicketNo());

        String nameUserapprove = "";
        for (User user : userList) {
            if (nameUserapprove.length() < 1) {
                nameUserapprove = "Waiting : " + user.getSsoId();
            } else {
                nameUserapprove = nameUserapprove + ", " + user.getSsoId();
            }
        }

        if (nameUserapprove.isEmpty() || !nameUserapprove.contains("Waiting")) {
            nameUserapprove = "Empty";
        }
        return nameUserapprove;

    }

    

}
