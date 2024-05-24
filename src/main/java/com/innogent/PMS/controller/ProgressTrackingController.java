package com.innogent.PMS.controller;

import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgressTrackingController {
    @Autowired
    ProgressTrackingService progressTrackingService;
      //notes aur video ko add karne k liya
    @PostMapping("/addMeetingNotesAndRecording")
    public ResponseEntity<?> addNotes(@RequestBody ProgressTracking progressTracking)
    {
        this.progressTrackingService.addNotesAndRecording(progressTracking);
        return ResponseEntity.ok(progressTracking);
    }
    //get meeting data by meeting id
    @GetMapping("/getMeetingNotesAndRecordingById/{id}")
    public ResponseEntity<?> getById(@PathVariable String id)
    {
        return this.progressTrackingService.getById(Long.parseLong(id));
    }
}
