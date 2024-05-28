package com.innogent.PMS.dto;

import com.innogent.PMS.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProgressTrackingDto {
    private Long meetingId;
    private Integer userId;
    private Integer managerId;
    private LocalDateTime date;
    private String notes;
    private String recording;
}
