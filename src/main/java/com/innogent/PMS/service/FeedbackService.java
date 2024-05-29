package com.innogent.PMS.service;

import com.innogent.PMS.entities.Feedback;
import com.innogent.PMS.enums.EvaluationType;

import java.util.List;

public interface FeedbackService {
    public Feedback saveFeedback(Feedback feedback);
//    public List<Feedback> getFeedbackByUserId(Integer userId);
//    public List<Feedback> getFeedbackByTypeAndUser(EvaluationType feedbackType, Integer userId);
}
