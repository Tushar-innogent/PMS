package com.innogent.PMS.controller;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController  {
    @Autowired
    private GoalService goalService;
    //add new goal
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addNewGoal(@RequestBody GoalDto goalDto, @PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(goalService.addGoal(goalDto, userId));
    }
    //get goal by goal id
    @GetMapping("/{goalId}")
    public ResponseEntity<?> getUserGoals(@PathVariable Long goalId){
        GoalDto goalDto = goalService.findGoalByGoalId(goalId);
        if(goalDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        return ResponseEntity.status(HttpStatus.OK).body(goalDto);
    }
    //get list of goals of user by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllGoalsOfUser(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(goalService.listAllGoalsOfEmployee(userId));
    }
    //update goal
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGoalByGoalId(@PathVariable Long id, @RequestBody GoalDto goalDto){
        GoalDto result = goalService.editGoal(id, goalDto);
        if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Present");
        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
