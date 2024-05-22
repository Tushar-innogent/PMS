package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.User;
import com.innogent.PMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        userRepository.save(user);
        return "AddEmployee Successfully!!";
    }
}
