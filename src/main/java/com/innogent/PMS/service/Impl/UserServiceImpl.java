package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.Role;
import com.innogent.PMS.entities.User;
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

//    public ResponseEntity<String> register(User user){
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//        if (existingUser.isPresent()) {
//            return ResponseEntity.badRequest().body("Email already in use");
//        }
//        Role existingRole = roleRepository.findByName(user.getRole().getName());
//        if (existingRole != null) {
//            user.setRole(existingRole);
//        } else {
//            roleRepository.save(user.getRole());
//        }
//        if (user.getRole().getName().toString().equals("USER") && userRepository.findByEmail(user.getManagerEmail()).isEmpty()) {
//            return ResponseEntity.badRequest().body("ManagerEmail not present");
//        }
//        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully");
//    }

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
            user.setPassword(newPassword); // Ideally, you should hash the password before saving
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

}
