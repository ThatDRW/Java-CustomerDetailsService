package com.thatdrw.customerdetailsservice.service;


import com.thatdrw.customerdetailsservice.entity.User;

public interface UserService {
    
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);

}