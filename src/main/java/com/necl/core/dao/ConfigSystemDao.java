/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.dao;

import com.necl.core.model.ConfigSystem;

/**
 *
 * @author C13.207
 */
public interface ConfigSystemDao {
    public ConfigSystem findByKey(String key) throws Exception;
    public boolean update(ConfigSystem configSystem) throws Exception;
}
