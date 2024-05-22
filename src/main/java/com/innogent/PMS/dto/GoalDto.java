package com.innogent.PMS.dto;

import com.innogent.PMS.enums.GoalStatus;
import com.innogent.PMS.enums.GoalType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class GoalDto {
    private Long goalId;
    private GoalType goalType;
    private String description;
    private GoalStatus goalStatus;
}
