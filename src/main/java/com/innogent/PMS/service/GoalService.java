package com.innogent.PMS.service;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;

public interface GoalService {
    public String addGoal(GoalDto goal);
    public GoalDto findGoalByEmpId(Integer employeeId);
}
