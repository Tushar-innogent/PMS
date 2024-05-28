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
    public String finalizeGoal(Long goalId, Integer managerId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(goal.isPresent() && goal.get().getUser().getManagerId().equals(managerId)){
            Stage stage = stageRepository.findByGoals(goal.get());
            if(stage.getStageName().name().equals(StageName.GOAL_SETTING.name()) && stage.getStageStatus().name().equals(StageStatus.PENDING.name())){
                stage.setStageStatus(StageStatus.FINALISED);
                stageRepository.save(stage);
                return "Goal finalised";
            }
        }
        return "Goal not present!";
    }
}
