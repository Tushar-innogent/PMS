package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.Feedback;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.enums.EvaluationType;
import com.innogent.PMS.repository.FeedbackRepository;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Feedback saveFeedback(Feedback feedback){
        Optional<User> user =userRepository.findById(feedback.getUser().getUserId());
        Optional<User> provider=userRepository.findById(feedback.getProvider().getUserId());
        Optional<Goal> goal=goalRepository.findById(feedback.getGoal().getGoalId());

        if(user.isPresent() && provider.isPresent() && goal.isPresent()){
            return feedbackRepository.save(feedback);
        }
        else{
            System.out.println("Invalid user,provider or goal Id");
            return null;
        }
    }

//    @Override
//    public List<Feedback> getFeedbackByUserId(Integer userId) {
//        return feedbackRepository.findByUserUserId(userId);
//    }
//
//    public List<Feedback> getFeedbackByTypeAndUser(EvaluationType feedbackType, Integer userId) {
//        return feedbackRepository.findByFeedbackTypeAndUserUserId(feedbackType, userId);
//    }
}