package com.innogent.PMS.service;

import com.innogent.PMS.dto.StageTimeLineDto;
import com.innogent.PMS.entities.Stage;

public interface StageTimeLineService {
    public Stage createStage(StageTimeLineDto stageTimeLineDto);
}
