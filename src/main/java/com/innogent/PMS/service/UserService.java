package com.innogent.PMS.service;

import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //To register new user
    public UserDto register(UserDto userDto);
    public ResponseEntity<List<User>> getALL();
    public ResponseEntity<User> getEmployeeById(Integer empId);
//    public ResponseEntity<String> updateUser(User user);
    public Optional<User> signIn(String email, String password);
    public Optional<User> updatePassword(String email, String newPassword);
}
