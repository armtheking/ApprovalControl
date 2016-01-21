/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.dao;

import com.necl.core.model.Position;

/**
 *
 * @author Saspallow
 */
public interface PositionDao {

    public Position findById(String id) throws Exception;
}
