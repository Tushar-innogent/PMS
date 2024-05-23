package com.innogent.PMS.service;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GoalService {
    // add a new goal
    public Goal addGoal(GoalDto goal);
    // find a goal by goal id
    public GoalDto findGoalByEmpId(Integer employeeId);
    // edit goal
    public String editGoal(Integer employeeId);
    // list all goals of an employee
    public List<GoalDto> listAllGoalsOfEmployee(Integer employeeId);
}
