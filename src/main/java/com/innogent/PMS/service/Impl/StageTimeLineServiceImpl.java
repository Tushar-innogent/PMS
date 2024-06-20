package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.entities.Timeline;
import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.repository.StageRepository;
import com.innogent.PMS.repository.TimelineRepository;
import com.innogent.PMS.service.StageTimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StageTimeLineServiceImpl implements StageTimeLineService {
    private static final Logger logger = Logger.getLogger(StageTimeLineServiceImpl.class.getName());


    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public Stage createStage(StageTimeLineDto stageTimeLineDto) {
        // Create and save the Stage entity first
        Stage stage = new Stage();
        stage.setStageName(StageName.valueOf(stageTimeLineDto.getStageName()));
        stage.setDescription(stageTimeLineDto.getDescription());
        stage.setIsActive(stageTimeLineDto.getIsActive());  // Correctly set isActive

        stage = stageRepository.save(stage); // Save the stage first

        // Set timeline with start and end dates
        LocalDateTime startDate = stageTimeLineDto.getStartDate();// Get current date and time
        LocalDateTime endDate = stageTimeLineDto.getEndDate();

        Timeline timeline = new Timeline();
        timeline.setStartDate(startDate);
        timeline.setEndDate(endDate);
        timeline.setStages(stage);

        // Save timeline
        timeline = timelineRepository.save(timeline);

        // Associate the saved timeline with the stage
        stage.setTimeline(timeline);
        stage = stageRepository.save(stage); // Save the stage again with the associated timeline

        return stage;
    }

    @Override
    public Stage updateStage(StageTimeLineDto stageTimeLineDto) {
        logger.info("Updating Stage with StageTimeLineDto: " + stageTimeLineDto.toString());

        Optional<Stage> optionalStage = stageRepository.findByStageName(StageName.valueOf(stageTimeLineDto.getStageName()));
        if (optionalStage.isPresent()) {
            Stage stage = optionalStage.get();
            stage.setDescription(stageTimeLineDto.getDescription());
            stage.setIsActive(stageTimeLineDto.getIsActive());

            Timeline timeline = stage.getTimeline();
            if (timeline == null) {
                timeline = new Timeline();
                timeline.setStages(stage);
            }
            timeline.setStartDate(stageTimeLineDto.getStartDate());
            timeline.setEndDate(stageTimeLineDto.getEndDate());
            timelineRepository.save(timeline);

            stage.setTimeline(timeline);
            return stageRepository.save(stage);
        } else {
            throw new IllegalArgumentException("Stage with name " + stageTimeLineDto.getStageName() + " not found");
        }
    }

    @Override
    public Stage getStageByName(String stageName) {
        return stageRepository.findByStageName(StageName.valueOf(stageName))
                .orElseThrow(() -> new IllegalArgumentException("Stage with name " + stageName + " not found"));
    }

    @Override
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public StageTimeLineDto getStageWithTimelineByName(String stageName) {
        Stage stage = stageRepository.findByStageName(StageName.valueOf(stageName))
                .orElseThrow(() -> new IllegalArgumentException("Stage with name " + stageName + " not found"));

        StageTimeLineDto dto = new StageTimeLineDto();
        dto.setStageId(stage.getStageId());
        dto.setStageName(stage.getStageName().name());
        dto.setDescription(stage.getDescription());
        dto.setIsActive(stage.getIsActive());

        Timeline timeline = stage.getTimeline();
        if (timeline != null) {
            dto.setStartDate(timeline.getStartDate());
            dto.setEndDate(timeline.getEndDate());
        }

        return dto;
    }

    @Override
    public List<StageTimeLineDto> getAllStagesWithTimeline() {
        List<Stage> stages = stageRepository.findAll();
        return stages.stream().map(stage -> {
            StageTimeLineDto dto = new StageTimeLineDto();
            dto.setStageId(stage.getStageId());
            dto.setStageName(stage.getStageName().name());
            dto.setDescription(stage.getDescription());
            dto.setIsActive(stage.getIsActive());

            Timeline timeline = stage.getTimeline();
            if (timeline != null) {
                dto.setStartDate(timeline.getStartDate());
                dto.setEndDate(timeline.getEndDate());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
