package com.innogent.PMS.serviceImpl;

import com.innogent.PMS.entities.Employee;
import com.innogent.PMS.repository.EmployeeRepository;
import com.innogent.PMS.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee register(Employee employee) {
        return employeeRepository.save(employee);
    }
}
