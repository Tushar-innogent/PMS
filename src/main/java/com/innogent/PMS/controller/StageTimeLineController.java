package com.innogent.PMS.controller;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.service.StageTimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/stages")
@CrossOrigin(origins = "http://localhost:3000")
public class StageTimeLineController {
    @Autowired
    private StageTimeLineService stageTimeLineService;

    @PostMapping("/create")
    public ResponseEntity<Stage> createStage(@RequestBody StageTimeLineDto stageTimeLineDto) {
        Stage stage = stageTimeLineService.createStage(stageTimeLineDto);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Stage> updateStage(@RequestBody StageTimeLineDto stageTimeLineDto) {
        Stage updatedStage = stageTimeLineService.updateStage(stageTimeLineDto);
        return new ResponseEntity<>(updatedStage, HttpStatus.OK);
    }

    @GetMapping("/{stageName}")
    public ResponseEntity<Stage> getStageByName(@PathVariable String stageName) {
        Stage stage = stageTimeLineService.getStageByName(stageName);
        return new ResponseEntity<>(stage, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Stage>> getAllStages() {
        List<Stage> stages = stageTimeLineService.getAllStages();
        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @GetMapping("/timeline/{stageName}")
    public ResponseEntity<StageTimeLineDto> getStageWithTimelineByName(@PathVariable String stageName) {
        StageTimeLineDto dto = stageTimeLineService.getStageWithTimelineByName(stageName);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/timeline/all")
    public ResponseEntity<List<StageTimeLineDto>> getAllStagesWithTimeline() {
        List<StageTimeLineDto> dtos = stageTimeLineService.getAllStagesWithTimeline();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public List<StageTimeLineDto> getTimelines(@RequestParam String currentDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.parse(currentDateTime, formatter);
        List<StageTimeLineDto> activeTimelines = stageTimeLineService.getActiveTimelines(currentDate);
        if (activeTimelines.isEmpty()) {
            throw new RuntimeException("Time finish for all stages");
        }
        return activeTimelines;
    }
}
