/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.service;

import com.necl.core.model.UserProfile;
import java.util.List;

/**
 *
 * @author C13.207
 */
public interface UserProfileService {
    
    List<UserProfile> findAll();
    UserProfile findByType(String type);
    UserProfile findById(int id);
}
