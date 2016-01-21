/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.dao;

import com.necl.core.model.User;
import java.util.List;

/**
 *
 * @author C13.207
 */
public interface UserDao {
    public User findById(long id) throws Exception;
    public List<User> findAll() throws Exception;
    public boolean save(User user) throws Exception;
    public boolean update(User user) throws Exception;
    public boolean delete(User user) throws Exception;
    public User findBySso(String sso);
    public List<User> findMailUserApprove(String ticketNumber);
    public List<User> getMD() throws Exception;
    public String resetPassword(String oldPassword, String newPassword, String confirm_newpassword) throws Exception;
  
}
