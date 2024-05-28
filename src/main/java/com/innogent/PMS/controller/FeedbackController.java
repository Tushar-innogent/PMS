package com.innogent.PMS.controller;

import com.innogent.PMS.entities.Feedback;
import com.innogent.PMS.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class    FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/feedback/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable Integer userId){
        List<Feedback> feedbackList = feedbackService.getFeedbackByUserId(userId);
        if(feedbackList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feedbackList);
    }
}
