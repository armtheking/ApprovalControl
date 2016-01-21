/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.service;

import com.necl.core.model.FinanceChargeCode;
import java.util.List;

/**
 *
 * @author C13.207
 */
public interface FinChargeCodeService {

    public List<FinanceChargeCode> findAll();
    public List<FinanceChargeCode> findChargeCodeAsType(String idBranch, String typeItem);
    public FinanceChargeCode findById(Long id);
}
