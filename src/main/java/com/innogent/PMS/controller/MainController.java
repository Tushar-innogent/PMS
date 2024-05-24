package com.innogent.PMS.controller;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Role;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.repository.RoleRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MainController {;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Our Performance Manager Application!!";
    }

    // To add user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(userDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getALL(){
        return this.userService.getALL();
    }

    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<User> getEmployeeById(@PathVariable String empId) {
        return this.userService.getEmployeeById(Integer.parseInt(empId));
    }

//    @PutMapping("/updateUser")
//    public ResponseEntity<String> updateUser(@RequestBody User user) {
//        return  this.userService.updateUser(user);
//    }


    @PostMapping("/userSignIn")
    public ResponseEntity<Map<String, Object>> signInUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> userOptional = userService.signIn(email, password);

        Map<String, Object> response = new HashMap<>();
        if (userOptional.isPresent()) {
            response.put("message", "Sign-in successful");
            response.put("user", userOptional.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(401).body(response);
        }
    }
//    update not woring

//    @PostMapping("/updateUserPassword")
//    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String newPassword = request.get("newPassword");
//
//        Optional<User> userOptional = userService.updatePassword(email, newPassword);
//
//        Map<String, Object> response = new HashMap<>();
//        if (userOptional.isPresent()) {
//            response.put("message", "Password updated successfully");
//            response.put("user", userOptional.get());
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Email not found");
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

}