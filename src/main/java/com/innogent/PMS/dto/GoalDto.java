package com.innogent.PMS.dto;

import com.innogent.PMS.enums.GoalStatus;
import com.innogent.PMS.enums.GoalType;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoalDto {
    private Long goalId;
    private String goalName;
    private GoalType goalType;
    private String description;
    private String measurable;
    private Integer userId;
    private LocalDateTime setDate;
    private LocalDateTime endDate;
    private GoalStatus goalStatus;
    private Integer selfRating;
    private String selfComments;
    private Integer managerRating;
    private String managerComments;
}
