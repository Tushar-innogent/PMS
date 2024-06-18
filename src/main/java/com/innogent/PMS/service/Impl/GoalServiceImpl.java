package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.enums.GoalStatus;
import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.GoalRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.EmailService;
import com.innogent.PMS.service.GoalService;
import com.innogent.PMS.service.StageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private static final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final CustomMapper customMapper;
    private final StageService stageService;
    private final EmailService emailService;

    @Override
    public GoalDto addPersonalGoal(GoalDto goalDto, Integer userId) throws NoSuchUserExistsException {
        log.info("Performing personal goal addition!");
        User user = userRepository.findById(goalDto.getUserId()).orElseThrow(()->new NoSuchUserExistsException("No User Present With Id : "+goalDto.getUserId(), HttpStatus.NOT_FOUND));
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setUser(user);
        goal.setGoalStatus(GoalStatus.CREATED);
        if(goal.getGoalId() == null) {
            Goal result = goalRepository.save(goal);
            return customMapper.goalEntityToGoalDto(result);
        }
        throw new RuntimeException("rewriting data may be caused!");
    }
    @Override
    public GoalDto addOrganisationalGoal(GoalDto goalDto, Integer managerId) throws NoSuchUserExistsException {
        log.info("Performing Organisational goal addition!");
        User user = userRepository.findById(goalDto.getUserId()).orElseThrow(()->new NoSuchUserExistsException("No User Present With Id : "+goalDto.getUserId(), HttpStatus.NOT_FOUND));
        if(!user.getManagerId().equals(managerId) || !goalDto.getGoalType().name().equalsIgnoreCase("ORGANISATIONAL")){
            throw new NoSuchUserExistsException("Only Manager Can Set Organisational Goal", HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        Goal goal = customMapper.goalDtoToEntity(goalDto);
        goal.setUser(user);
        goal.setGoalStatus(GoalStatus.CREATED);
        Goal result = goalRepository.save(goal);
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
        goal.setUser(optional.get().getUser());
        goal.setSetDate(optional.get().getSetDate());
        log.info("Sending email to hr on employee goal update!");
        emailService.sendEmail("tusharpatidar8580@gmail.com", "Employee Data Updated", "Manager has updated employee data!");
        return customMapper.goalEntityToGoalDto(goalRepository.save(goal));
    }
    @Override
    public List<GoalDto> listAllGoalsOfEmployee(Integer userId) throws NoSuchUserExistsException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){throw new NoSuchUserExistsException("User Not exist with provided id : "+userId, HttpStatus.NOT_FOUND);}
        // Filter out goal with isDeleted true
        List<Goal> goalsList = goalRepository.findAllByUser(user.get());
        List<Goal> activeGoals = goalsList.stream()
                .filter(goal -> !goal.isDeleted())
                .toList();
        return customMapper.goalListToGoalDto(activeGoals);
    }

    @Override
    public ResponseEntity<?> deleteGoal(Long goalId) throws NoSuchGoalExistsException {

        Optional<Goal> optionalGoal = goalRepository.findById(goalId);
        if (optionalGoal.isPresent()) {
            Goal goal = optionalGoal.get();
            goal.setDeleted(true);
            goalRepository.save(goal);
            return ResponseEntity.ok("Goal deleted successfully");
        } else {
            throw new NoSuchGoalExistsException("No Goal Present or Id is invalid : "+goalId, HttpStatus.NOT_FOUND);
        }
    }

}
