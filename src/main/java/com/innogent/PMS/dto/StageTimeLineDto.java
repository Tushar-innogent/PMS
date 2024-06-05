package com.innogent.PMS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StageTimeLineDto {
    private String stageName;
    private String description;
    private LocalDateTime endDate;
    private boolean isActive;
}
