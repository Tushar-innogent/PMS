package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomMapper customMapper;

    @Override
    public Goal addGoal(GoalDto goalDto) {
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        return goalRepository.save(goal);
    }
    @Override
    public GoalDto findGoalByEmpId(Integer employeeId) {
        User user =  userRepository.findById(employeeId).get();
        if(user == null) return null;
        Goal goal = goalRepository.findByUser(user);
        return customMapper.goalEntityToGoal(goal);
    }

    @Override
    public String editGoal(Integer employeeId) {
        return "";
    }

    @Override
    public List<GoalDto> listAllGoalsOfEmployee(Integer employeeId) {
        return List.of();
    }

}
