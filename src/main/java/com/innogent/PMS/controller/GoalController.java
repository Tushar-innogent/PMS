package com.innogent.PMS.controller;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.service.GoalService;
import com.innogent.PMS.service.StageService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Controller to handle goal module api's
 */
@Log4j2
@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController  {
    @Autowired
    private GoalService goalService;
    @Autowired
    private StageService stageService;

    /**
     * @param goalDto : goal data to save in db
     * @param userId : id of user whose data is saved
     * @return goal data if saved
     * @throws NoSuchUserExistsException
     */
    //add personal goal
    @PostMapping("/addPersonal/{userId}")
    public ResponseEntity<?> addPersonalGoal(@RequestBody GoalDto goalDto, @PathVariable Integer userId) throws NoSuchUserExistsException {
        log.info("Adding personal goals!");
        return ResponseEntity.status(HttpStatus.OK).body(goalService.addPersonalGoal(goalDto, userId));
    }
    //add Organisational goal
    @PostMapping("/addOrganisational/{managerId}")
    public ResponseEntity<?> addOrgGoal(@RequestBody GoalDto goalDto, @PathVariable Integer managerId) throws NoSuchUserExistsException {
        log.info("Adding Organisational goals!");
        return ResponseEntity.status(HttpStatus.OK).body(goalService.addOrganisationalGoal(goalDto, managerId));
    }
    //get goal by goal id
    @GetMapping("/{goalId}")
    public ResponseEntity<?> getUserGoals(@PathVariable Long goalId){
        GoalDto goalDto = goalService.findGoalByGoalId(goalId);
        if(goalDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal doesn't exist!");
        return ResponseEntity.status(HttpStatus.OK).body(goalDto);
    }
    //get list of goals of user by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllGoalsOfUser(@PathVariable Integer userId) throws NoSuchUserExistsException {
        List<GoalDto> goals = goalService.listAllGoalsOfEmployee(userId);
        if(goals == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goals doesn't exist for required user!");
        return ResponseEntity.status(HttpStatus.OK).body(goals);
    }
    //update goal by goal id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGoalByGoalId(@PathVariable String id, @RequestBody GoalDto goalDto) throws NoSuchGoalExistsException {
        log.info("updating records of id :"+id);
        GoalDto result = goalService.editGoal(Long.parseLong(id), goalDto);
        if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Present");
        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }
//    //delete a goal
//    @DeleteMapping("/delete/{goalId}")
//    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) throws NoSuchGoalExistsException {
//        return ResponseEntity.status(HttpStatus.OK).body(goalService.deleteGoal(goalId));
//    }

    //softDelete
    @DeleteMapping("delete/{goalId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long goalId) throws NoSuchGoalExistsException {
        return goalService.deleteGoal(goalId);
    }

    //to add self feedback on individual goal
    @PutMapping("/selfFeedback/{goalId}")
    public ResponseEntity<?> addSelfFeedback(@PathVariable String goalId, @RequestBody GoalDto goalDto) throws NoSuchGoalExistsException {
        log.info("setting goal feedback");
        GoalDto result = goalService.addSelfFeedback(Long.parseLong(goalId), goalDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //to add self feedback on individual goal
    @PutMapping("/managerFeedback/{goalId}")
    public ResponseEntity<?> addManagerFeedback(@PathVariable String goalId, @RequestBody GoalDto goalDto) throws NoSuchGoalExistsException {
        log.info("setting goal feedback");
        GoalDto result = goalService.addManagerFeedback(Long.parseLong(goalId), goalDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}