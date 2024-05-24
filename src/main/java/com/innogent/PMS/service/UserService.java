package com.innogent.PMS.service;

import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //To register new user
    public UserDto register(UserDto userDto);
    //get all users
    public ResponseEntity<List<User>> getALL();
    //get user by userId
    public ResponseEntity<User> getEmployeeById(Integer empId);
    //update particular user
    public ResponseEntity<String> updateUser(UserDto userDto,Integer userId);
    //login
    public Optional<User> signIn(String email, String password);
    //forgot password
    public Optional<User> updatePassword(String email, String newPassword);
}
