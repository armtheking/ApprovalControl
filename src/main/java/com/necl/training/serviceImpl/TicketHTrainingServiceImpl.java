package com.necl.training.serviceImpl;

import com.necl.core.model.Item;
import com.necl.core.model.User;
import com.necl.core.service.ItemService;
import com.necl.core.service.UserService;
import com.necl.training.dao.TicketHTrainingDao;
import com.necl.training.model.TicketHTraining;
import com.necl.training.service.TicketHTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketHTrainingServiceImpl implements TicketHTrainingService {

    @Autowired
    private TicketHTrainingDao ticketHTrainingDao;

    @Autowired
    private UserService userService;

    @Autowired
    ItemService itemService;

    @Override
    public boolean save(TicketHTraining ticketHTraining) throws Exception {
        return ticketHTrainingDao.save(setTicketH(ticketHTraining));
    }

    private TicketHTraining setTicketH(TicketHTraining ticketHTraining) throws Exception {
  
        User user = userService.findBySso(ticketHTraining.getTicketHeader().getApplicationName());
        Item item = itemService.findItemTicket(ticketHTraining.getTicketHeader().getReqTotalAmt(), ticketHTraining.getTicketHeader().getTicketType());
        System.out.println("organizeby3: "+ticketHTraining.getOrganizeBy());
        ticketHTraining.getTicketHeader().setDivisionCode(user.getDivisionCode());
        ticketHTraining.getTicketHeader().setSectionCode(user.getSectionCode());
        ticketHTraining.getTicketHeader().setSubSectionCode(user.getSubSectionCode());
        ticketHTraining.getTicketHeader().setApplicationID(String.valueOf(user.getId()));
        ticketHTraining.getTicketHeader().setApplicationPosition(user.getPositionCode());

        ticketHTraining.getTicketHeader().setItem(item.getItem());
        ticketHTraining.getTicketHeader().setCategoryId(item.getCategoryId());
        ticketHTraining.getTicketHeader().setSubCategoryId(item.getSubCategoryId());
        return ticketHTraining;
    }

    @Override
    public TicketHTraining findByTicketNo(String ticketNo) throws Exception {
       return ticketHTrainingDao.findByTicketNo(ticketNo);
    }

    @Override
    public boolean update(TicketHTraining ticketHTraining) throws Exception {
         return ticketHTrainingDao.update(ticketHTraining);
    }

    @Override
    public boolean delete(TicketHTraining ticketHTraining) throws Exception {
        return ticketHTrainingDao.delete(ticketHTraining);
    }

}
