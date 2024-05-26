package com.innogent.PMS.service;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GoalService {
    // To create a new goal
    public Goal addGoal(GoalDto goal, Integer userId);
    // Retrieve goal by goal id
    public GoalDto findGoalByGoalId(Long goalId);
    // To Update goal  by goal id
    public GoalDto editGoal(Long goalId, GoalDto goalDto);
    // list all goals of an employee
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId);
}
