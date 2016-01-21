package com.necl.training.dao;

import com.necl.training.model.TicketHTraining;

public interface TicketHTrainingDao {
        public boolean save(TicketHTraining ticketHTraining) throws Exception;
        public TicketHTraining findByTicketNo(String ticketNo) throws Exception;
        public boolean update(TicketHTraining ticketHTraining) throws Exception;
        public boolean delete(TicketHTraining ticketHTraining) throws Exception;
}
