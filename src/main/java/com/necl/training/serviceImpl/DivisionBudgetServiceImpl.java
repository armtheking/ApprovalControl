package com.necl.training.serviceImpl;

import com.necl.training.dao.DivisionBudgetDao;
import com.necl.training.dao.TicketHTrainingDao;
import com.necl.training.model.DivisionSumBudgetList;
import com.necl.training.service.DivisionBudgetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class DivisionBudgetServiceImpl implements DivisionBudgetService{

    @Autowired
    private DivisionBudgetDao divisionBudgetDao;
    
    @Override
    public List sumBudget() throws Exception {
        return divisionBudgetDao.sumBudget();
     }

    @Override
    public void editMaxBudget(DivisionSumBudgetList divisionSumBudgetList) throws Exception {
        divisionBudgetDao.editMaxBudget(divisionSumBudgetList);
    }

 
    
}
