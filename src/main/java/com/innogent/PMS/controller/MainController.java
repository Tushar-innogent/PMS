package com.innogent.PMS.controller;

import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {;
    @Autowired
    private UserService userService;

    @PreAuthorize("ADMIN")
    @GetMapping("/home")
    public String home() {
        return "Welcome to Our Performance Manager Application!!";
    }

    //  Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(userDto));
    }

    // fetch all users
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getALL(){
        return this.userService.getALL();
    }

    //get particular user by id
    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<User> getEmployeeById(@PathVariable String empId) throws NoSuchUserExistsException {
        return this.userService.getEmployeeById(Integer.parseInt(empId));
    }

    //update user by id
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto,@PathVariable Integer userId) {
        return  this.userService.updateUser(userDto,userId);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signInUser(@RequestBody Map<String, String> request) {
        Optional<User> userOptional = userService.signIn(request.get("email"), request.get("password"));
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

    //forgot password
    @PostMapping("/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> request) {
        Optional<User> userOptional = userService.updatePassword(request.get("email"), request.get("newPassword"));
        Map<String, Object> response = new HashMap<>();
        if (userOptional.isPresent()) {
            response.put("message", "Password updated successfully");
            response.put("user", userOptional.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    //user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getByUserEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.userByEmail(email));
    }

}