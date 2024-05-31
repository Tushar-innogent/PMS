package com.innogent.PMS.dto;

import com.innogent.PMS.enums.GoalType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDto {
    private GoalType goalType;
    private String description;
    private Integer userId;
    private LocalDate date;
}
