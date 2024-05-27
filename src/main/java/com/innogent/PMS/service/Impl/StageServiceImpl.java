package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.enums.StageStatus;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.StageRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StageServiceImpl implements StageService {
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Override
    public Stage setStage(Stage stage) {
         return stageRepository.save(stage);
    }

    @Override
    public Stage finalizeGoal(Integer userId, Integer managerId, Long goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        Optional<User> user = userRepository.findById(userId);
        if(goal.isPresent() && user.isPresent() && user.get().getManagerId().equals(managerId)){
            Stage stage = stageRepository.findByGoal(goal.get());
            if(stage.getStageName().equals(StageName.GOAL_SETTING) && stage.getStageStatus().equals(StageStatus.PENDING)){
                stage.setStageStatus(StageStatus.FINALISED);
            }
        }

        return null;
    }
}
