package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.enums.StageStatus;
import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.GoalService;
import com.innogent.PMS.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private StageService stageService;

    @Override
    public GoalDto addPersonalGoal(GoalDto goalDto, Integer userId) throws NoSuchUserExistsException {
        Optional<User> user = userRepository.findById(goalDto.getUserId());
        if(user.isEmpty()){
            throw new NoSuchUserExistsException("No User Present With Id : "+goalDto.getUserId(), HttpStatus.NOT_FOUND);
        }
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setUser(user.get());
        if(goal.getGoalId() == null) {
            Goal result = goalRepository.save(goal);
            return customMapper.goalEntityToGoalDto(result);
        }
        throw new RuntimeException("rewriting data may be caused!");
//        stageService.setStage(new Stage(StageName.GOAL_SETTING, StageStatus.PENDING, result)); // To set the stage of initial goal when declared

    }
    @Override
    public GoalDto addOrganisationalGoal(GoalDto goalDto, Integer managerId) throws NoSuchUserExistsException {
        Optional<User> user = userRepository.findById(goalDto.getUserId());
        if(user.isEmpty() || !user.get().getManagerId().equals(managerId) || !goalDto.getGoalType().name().equalsIgnoreCase("ORGANISATIONAL")){
            throw new NoSuchUserExistsException("Only Manager Can Set Organisational Goal", HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setUser(user.get());
        Goal result=goalRepository.save(goal);
//        stageService.setStage(new Stage(StageName.GOAL_SETTING, StageStatus.FINALISED, goal)); // To set the stage of initial goal when declared
        return customMapper.goalEntityToGoalDto(result);
    }
    @Override
    public GoalDto findGoalByGoalId(Long goalId) {
        Optional<Goal> optional =  goalRepository.findById(goalId);
        return optional.map(goal -> customMapper.goalEntityToGoalDto(goal)).orElse(null);
    }
    @Override
    public GoalDto editGoal(Long goalId, GoalDto goalDto) throws NoSuchGoalExistsException {
        Optional<Goal> optional = goalRepository.findById(goalId);
        if(optional.isEmpty()) throw new NoSuchGoalExistsException("No Goal Present With Id : "+goalId, HttpStatus.NOT_FOUND);
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setGoalId(goalId);
        return customMapper.goalEntityToGoalDto(goalRepository.save(goal));
    }
    @Override
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId) {
        Optional<List<Goal>> goalsList = goalRepository.findAllByUser(userRepository.findById(userId).get());
        return goalsList.map(goal -> customMapper.goalListToGoalDto(goal)).orElse(null);
    }

    @Override
    public String deleteGoal(Long goalId) throws NoSuchGoalExistsException {
        if(goalRepository.existsById(goalId)){
            goalRepository.deleteById(goalId);
            return "Record Deleted!";
        }
        else{
            throw new NoSuchGoalExistsException("No Goal Present With Id : "+goalId, HttpStatus.NOT_FOUND);
        }
    }

}
