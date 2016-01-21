/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.dao;

import com.necl.core.model.Section;

/**
 *
 * @author Saspallow
 */
public interface SectionDao {
    public Section findById(String division, String section) throws Exception;
}
