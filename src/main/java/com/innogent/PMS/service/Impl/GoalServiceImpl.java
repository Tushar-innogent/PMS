package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public GoalDto findGoalByGoalId(Long goalId) {
        Optional<Goal> optional =  goalRepository.findById(goalId);
        return optional.map(goal -> customMapper.goalEntityToGoalDto(goal)).orElse(null);
    }
    @Override
    public GoalDto editGoal(Long goalId, GoalDto goalDto) {
        Optional<Goal> optional = goalRepository.findById(goalId);
        if(optional.isEmpty()) return null;
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setGoalId(goalId);
        return customMapper.goalEntityToGoalDto(goalRepository.save(goal));
    }
    @Override
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId) {
        Optional<List<Goal>> goalsList = goalRepository.findAllByUser(userRepository.findById(userId).get());
        return goalsList.map(goal -> customMapper.goalListToGoalDto(goal)).orElse(null);
    }

}
