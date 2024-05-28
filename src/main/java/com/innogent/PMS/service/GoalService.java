package com.innogent.PMS.service;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GoalService {
    // To create a new personal goal
    public GoalDto addPersonalGoal(GoalDto goal, Integer userId);
    // To create a new organisational goal
    public GoalDto addOrganisationalGoal(GoalDto goal, Integer managerId);
    // Retrieve goal by goal id
    public GoalDto findGoalByGoalId(Long goalId);
    // To Update goal  by goal id
    public GoalDto editGoal(Long goalId, GoalDto goalDto);
    // list all goals of an employee
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId);
    // delete goal
    public String deleteGoal(Long goalId);
}