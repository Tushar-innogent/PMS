package com.innogent.PMS.service;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StageTimeLineService {
    public Stage createStage(StageTimeLineDto stageTimeLineDto);
    Stage updateStage(Integer timelineId,StageTimeLineDto stageTimeLineDto);
    Stage getStageByName(String stageName);
    List<Stage> getAllStages();
    public List<StageTimeLineDto> getTimelinesByStageName(String stageName);
    List<StageTimeLineDto> getAllTimelinesWithStages();
    public List<StageTimeLineDto> getActiveTimelines(LocalDateTime currentDate);
}
