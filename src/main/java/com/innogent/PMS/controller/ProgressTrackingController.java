package com.innogent.PMS.controller;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin
public class ProgressTrackingController {
    @Autowired
    ProgressTrackingService progressTrackingService;

    //get meeting data by meeting id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id)
    {
        return this.progressTrackingService.getById(Long.parseLong(id));
    }

    @PostMapping("/add/{empId}")
    public ResponseEntity<?> addProgressTracking(@PathVariable Integer empId,@RequestBody ProgressTrackingDto trackingDto)  {
        //System.out.println("added progrees");
        return this.progressTrackingService.addProgressTracking(empId,trackingDto);
    }
    //get data by employee id
    @GetMapping("/get/user/{employeeId}")
   public ResponseEntity<?> getProgressTracking(@PathVariable Integer employeeId)  {
       return this.progressTrackingService.getProgressTracking(employeeId);
   }

   @PutMapping("/updateData/{meetingId}")
   public ResponseEntity<?> updateProgressTracking(@PathVariable String meetingId,@RequestBody ProgressTrackingDto progressTrackingDto)
   {
       return  this.progressTrackingService.editProgressTracking(Long.parseLong(meetingId),progressTrackingDto);
   }

   @GetMapping("/getAllTrackingData")
   public ResponseEntity<?> getAllData()
   {
       return this.progressTrackingService.getAllData();
   }
   @DeleteMapping("/deleteData/{id}")
   public ResponseEntity<?> deleteByMeetingId(@PathVariable String id)
   {
       return this.progressTrackingService.deleteByMeetingId(Long.parseLong(id));
   }

}
