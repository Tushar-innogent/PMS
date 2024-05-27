package com.innogent.PMS.service;

import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.Stage;

public interface StageService {
    //To set stage for a goal
    public Stage setStage(Stage stage);
    //Finalize the goal
    public Stage finalizeGoal(Integer userId, Integer managerId, Long goalId);
}
