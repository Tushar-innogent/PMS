package com.innogent.PMS.service;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GoalService {
    // To create a new personal goal
    public GoalDto addPersonalGoal(GoalDto goal, Integer userId) throws NoSuchUserExistsException;
    // To create a new organisational goal
    public GoalDto addOrganisationalGoal(GoalDto goal, Integer managerId) throws NoSuchUserExistsException;
    // Retrieve goal by goal id
    public GoalDto findGoalByGoalId(Long goalId);
    // To Update goal  by goal id
    public GoalDto editGoal(Long goalId, GoalDto goalDto) throws NoSuchGoalExistsException;
    // list all goals of an employee
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId) throws NoSuchUserExistsException;
    // delete goal
    public ResponseEntity<?> deleteGoal(Long goalId) throws NoSuchGoalExistsException;
}