package com.innogent.PMS.controller;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressTrackingController {
    @Autowired
    ProgressTrackingService progressTrackingService;

    //get meeting data by meeting id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id)
    {
        return this.progressTrackingService.getById(Long.parseLong(id));
    }
    //path add with employee id
//   @PostMapping("/add/{empId}")
//    public ResponseEntity<?> addProgressTracking(@PathVariable Integer empId,@RequestBody ProgressTracking tracking)
//    {
//
//        return this.progressTrackingService.addProgressTracking(empId,tracking);
//    }
    @PostMapping("/add/{empId}")
    public ResponseEntity<?> addProgressTracking(@PathVariable Integer empId,@RequestBody ProgressTrackingDto trackingDto) throws NoSuchUserExistsException {

        return this.progressTrackingService.addProgressTracking(empId,trackingDto);
    }
    //get data by employee id
    @GetMapping("/get/user/{employeeId}")
   public ResponseEntity<?> getProgressTracking(@PathVariable Integer employeeId) throws NoSuchUserExistsException {
       return this.progressTrackingService.getProgressTracking(employeeId);
   }

}
