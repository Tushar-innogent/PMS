package com.innogent.PMS.entities;

import com.innogent.PMS.enums.TimelineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "timelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer timelineId;
    private TimelineType timelineType;
    private LocalDate startDate;
    private LocalDate endDate;
}
