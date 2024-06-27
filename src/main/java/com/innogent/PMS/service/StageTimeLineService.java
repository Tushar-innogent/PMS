package com.innogent.PMS.service;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StageTimeLineService {
    public Stage createStage(StageTimeLineDto stageTimeLineDto);
    Stage updateStage(StageTimeLineDto stageTimeLineDto);
    Stage getStageByName(String stageName);
    List<Stage> getAllStages();
    StageTimeLineDto getStageWithTimelineByName(String stageName);
    List<StageTimeLineDto> getAllStagesWithTimeline();
    public List<StageTimeLineDto> getActiveTimelines(LocalDateTime currentDate);
}
