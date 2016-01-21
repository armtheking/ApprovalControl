/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.serviceImpl;

import com.necl.core.dao.SectionDao;
import com.necl.core.model.Section;
import com.necl.core.service.SectionService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    @Autowired
    @Qualifier(value = "sectionDaoImpl")
    private SectionDao sectionDao;

    @Override
    public Section findById(String division, String section) throws Exception {
        return sectionDao.findById(division, section);
    }

}
