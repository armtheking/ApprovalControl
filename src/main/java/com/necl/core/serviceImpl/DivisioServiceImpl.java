package com.necl.core.serviceImpl;

import com.necl.core.dao.DivisionDao;
import com.necl.core.model.Division;
import com.necl.core.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DivisioServiceImpl implements DivisionService  {

    @Autowired
    private DivisionDao divisionDao;

    @Override
    public Division findById(String id) throws Exception {
        return divisionDao.findById(id);
    }
}
