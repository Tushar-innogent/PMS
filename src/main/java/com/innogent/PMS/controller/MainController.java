package com.innogent.PMS.controller;

import com.innogent.PMS.entities.Employee;
import com.innogent.PMS.repository.EmployeeRepository;
import com.innogent.PMS.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Our Performance Manager Application!!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return ResponseEntity.ok("employee added successfully");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> get(){
        List<Employee> employee = employeeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping("/getByEmpId/{empId}")
    public Employee getEmployeeById(@PathVariable String empId) {
        Integer id = Integer.parseInt(empId);
        return employeeRepository.findById(id).orElse(null);
    }
}
