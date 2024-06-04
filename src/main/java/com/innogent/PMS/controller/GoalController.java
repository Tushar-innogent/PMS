package com.innogent.PMS.controller;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.service.GoalService;
import com.innogent.PMS.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin
public class GoalController  {
    @Autowired
    private GoalService goalService;
    @Autowired
    private StageService stageService;

    //add personal goal
    @PostMapping("/addPersonal/{userId}")
    public ResponseEntity<?> addPersonalGoal(@RequestBody GoalDto goalDto, @PathVariable Integer userId) throws NoSuchUserExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(goalService.addPersonalGoal(goalDto, userId));
    }
    //add Organisational goal
    @PostMapping("/addOrganisational/{managerId}")
    public ResponseEntity<?> addOrgGoal(@RequestBody GoalDto goalDto, @PathVariable Integer managerId) throws NoSuchUserExistsException {
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
    public ResponseEntity<?> getAllGoalsOfUser(@PathVariable Integer userId){
        List<GoalDto> goals = goalService.listAllGoalsOfEmployee(userId);
        if(goals == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goals doesn't exist for required user!");
        return ResponseEntity.status(HttpStatus.OK).body(goals);
    }
    //update goal by goal id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGoalByGoalId(@PathVariable Long id, @RequestBody GoalDto goalDto) throws NoSuchGoalExistsException {
        GoalDto result = goalService.editGoal(id, goalDto);
        if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Present");
        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //delete a goal
    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) throws NoSuchGoalExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(goalService.deleteGoal(goalId));
    }
}
