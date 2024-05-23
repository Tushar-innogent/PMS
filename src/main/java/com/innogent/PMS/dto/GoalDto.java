package com.innogent.PMS.dto;

import com.innogent.PMS.enums.StageStatus;
import com.innogent.PMS.enums.GoalType;
import lombok.Data;

@Data
public class GoalDto {
    private GoalType goalType;
    private String description;
}
