package com.innogent.PMS.dto;

import com.innogent.PMS.enums.GoalType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDto {
    private Long goalId;
    private String goalName;
    private GoalType goalType;
    private String description;
    private String measurable;
    private Integer userId;
    private LocalDate setDate;
    private LocalDate endDate;
}
