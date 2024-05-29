package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Role;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.exception.customException.UserAlreadyExistsException;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.RoleRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomMapper customMapper;

    public UserDto register(UserDto userDto){
        System.out.println(userDto);
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User Already Exist With Provided Email!");
//            System.out.println("email already");
//            return null;
        }
        Role existingRole = roleRepository.findByName(userDto.getRole().getName());
        if (existingRole != null) {
            userDto.setRole(existingRole);
        } else {
            roleRepository.save(userDto.getRole());
        }
        Optional<User> manager = userRepository.findByEmail(userDto.getManagerEmail());
        if (userDto.getRole().getName().toString().equals("USER") && manager.isEmpty()) {
            System.out.println("manager email not present");
            return null;
        }
        User user = customMapper.userDtoToEntity(userDto);
        if(userDto.getRole().getName().toString().equals("USER")){
            user.setManagerId(manager.get().getUserId());
        }
        return customMapper.userEntityToDto(userRepository.save(user));
    }

    public ResponseEntity<List<User>> getALL(){
        List<User> user = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<User> getEmployeeById(Integer empId) throws NoSuchUserExistsException {
        return ResponseEntity.ok(userRepository.findById(empId).orElseThrow(()-> new NoSuchUserExistsException("Employee Not Present With Employee Id : "+empId, HttpStatus.NOT_FOUND)));
    }

    public ResponseEntity<String> updateUser(UserDto userDto, Integer userId) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Optional<User> userWithSameEmail = userRepository.findByEmail(userDto.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        Role existingRole = roleRepository.findByName(userDto.getRole().getName());
        if (existingRole != null) {
            userDto.setRole(existingRole);
        } else {
            userDto.setRole(roleRepository.save(userDto.getRole()));
        }
        Optional<User> manager = userRepository.findByEmail(userDto.getManagerEmail());
        if (userDto.getRole().getName().toString().equals("USER") && manager.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Manager email not present");
        }
        User user = existingUserOptional.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setContact(userDto.getContact());
        user.setJob(userDto.getJob());
        user.setSalary(userDto.getSalary());
        user.setHiredDate(userDto.getHiredDate());
        user.setRole(userDto.getRole());
        if (userDto.getRole().getName().toString().equals("USER") && manager.isPresent()) {
            user.setManagerId(manager.get().getUserId());
        } else {
            user.setManagerId(null);
        }
        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    public Optional<User> signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional;
        }
        return Optional.empty();
    }

    public Optional<User> updatePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
