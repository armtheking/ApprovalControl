/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.serviceImpl;

import com.necl.core.dao.FinChargeCodeDao;
import com.necl.core.model.FinanceChargeCode;
import com.necl.core.service.FinChargeCodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C13.207
 */
@Service
@Transactional
public class FinChargeCodeServiceImpl implements FinChargeCodeService {

    @Autowired
    FinChargeCodeDao finChargeCodeDao;

    @Override
    public List<FinanceChargeCode> findAll() {
        return finChargeCodeDao.findAll();
    }

    @Override
    public List<FinanceChargeCode> findChargeCodeAsType(String idBranch, String typeItem) {
        return finChargeCodeDao.findChargeCodeAsType(idBranch, typeItem);
    }

    @Override
    public FinanceChargeCode findById(Long id) {
        return finChargeCodeDao.findById(id);
    }

}
