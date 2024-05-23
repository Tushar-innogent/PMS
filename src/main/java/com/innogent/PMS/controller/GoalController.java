package com.innogent.PMS.controller;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GoalController  {
    @Autowired
    private GoalService goalService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewGoal(@RequestBody GoalDto goalDto){
        return ResponseEntity.status(HttpStatus.OK).body(goalService.addGoal(goalDto));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserGoals(@RequestParam Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(goalService.findGoalByEmpId(id));
    }
}
