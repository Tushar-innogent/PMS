package com.innogent.PMS.controller;

import com.innogent.PMS.entities.Feedback;
import com.innogent.PMS.enums.EvaluationType;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class    FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/save")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable Integer userId) {
//        List<Feedback> feedbackList = feedbackService.getFeedbackByUserId(userId);
//        if (feedbackList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(feedbackList);
//    }
//
//    @GetMapping("/by-type-and-user")
//    public List<Feedback> getFeedbackByTypeAndUser(
//            @RequestParam EvaluationType feedbackType,
//            @RequestParam Integer userId) {
//        return feedbackService.getFeedbackByTypeAndUser(feedbackType, userId);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable Integer userId) throws NoSuchUserExistsException {
        List<Feedback> feedbackList = feedbackService.retrieveUserFeedback(userId);
        if (feedbackList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(feedbackList);
    }

}


