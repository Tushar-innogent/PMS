package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.entities.Timeline;
import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.repository.StageRepository;
import com.innogent.PMS.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StageTimeLineServiceImpl {
    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private TimelineRepository timelineRepository;

//    @Override
    public Stage createStage(StageTimeLineDto stageTimeLineDto) {
        Stage stage = new Stage();
//        stage.setStageName(stageTimeLineDto.getStageName());
        stage.setStageName(StageName.valueOf(stageTimeLineDto.getStageName()));
        stage.setDescription(stageTimeLineDto.getDescription());
        stage.setIsActive(stageTimeLineDto.isActive());

        // Set timeline with start and end dates
        LocalDateTime startDate = LocalDateTime.now(); // Get current date and time
        LocalDateTime endDate = stageTimeLineDto.getEndDate();

        Timeline timeline = new Timeline();
        timeline.setStartDate(startDate);
        timeline.setEndDate(endDate);
        timeline.setStages(stage);

        // Save timeline first to maintain foreign key constraint
        timelineRepository.save(timeline);

        // Set stage with timeline
        stage.setTimeline(timeline);

        // Save stage
        return stageRepository.save(stage);
    }
}
