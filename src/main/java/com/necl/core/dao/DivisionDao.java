package com.necl.core.dao;

import com.necl.core.model.Division;


public interface DivisionDao {

    public Division findById(String id) throws Exception;
}
