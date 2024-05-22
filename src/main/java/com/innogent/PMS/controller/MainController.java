package com.innogent.PMS.controller;

import com.innogent.PMS.entities.User;
import com.innogent.PMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Our Performance Manager Application!!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> get(){
        List<User> user = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<User> getEmployeeById(@PathVariable String empId) {
        Integer id = Integer.parseInt(empId);
        return ResponseEntity.ok(userRepository.findById(id).orElse(null));
    }
}
