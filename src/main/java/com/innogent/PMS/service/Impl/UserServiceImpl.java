package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Role;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.RoleRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.UserService;
import jakarta.transaction.Transactional;
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

    // To add new user
    public UserDto register(UserDto userDto){
        System.out.println(userDto);
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("email already");
            return null;
        }
        Role existingRole = roleRepository.findByName(userDto.getRole().getName());
        if (existingRole != null) {
            userDto.setRole(existingRole);
        } else {
            roleRepository.save(userDto.getRole());
        }
        Optional<User> manager = userRepository.findByEmail(userDto.getManagerEmail());
        if (userDto.getRole().getName().toString().equals("USER") && manager.isEmpty()) {
            System.out.println(userDto);
            System.out.println("manager email not present");
            System.out.println(userDto);
            return null;
        }
        User user = customMapper.userDtoToEntity(userDto);
        user.setManagerId(manager.get().getUserId());
        return customMapper.userEntityToDto(user);
    }

    public ResponseEntity<List<User>> getALL(){
        List<User> user = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<User> getEmployeeById(Integer empId){
        return ResponseEntity.ok(userRepository.findById(empId).orElse(null));
    }

//    public ResponseEntity<String> updateUser(User user){
//        Role existingRole = roleRepository.findByName(user.getRole().getName());
//        if (existingRole != null) {
//            user.setRole(existingRole);
//        } else {
//            roleRepository.save(user.getRole());
//        }
//        System.out.println(userRepository.findByEmail(user.getEmail()));
//        if (user.getRole().getName().toString().equals("USER") && userRepository.findByEmail(user.getManagerEmail()) == null) {
//            return ResponseEntity.badRequest().body("ManagerEmail not present ");
//        }
//        userRepository.save(user);
//        return ResponseEntity.ok("User updated successfully");
//    }

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
            System.out.println(newPassword);
            user.setPassword(newPassword);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
