package com.innogent.PMS.controller;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.service.StageTimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/stages")
public class StageTimeLineController {
    @Autowired
    private StageTimeLineService stageService;

    @PostMapping("/create")
    public ResponseEntity<Stage> createStage(@RequestBody StageTimeLineDto stageTimeLineDto) {
        Stage stage = stageService.createStage(stageTimeLineDto);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }
}
