package com.innogent.PMS.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "timelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class

Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer timelineId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stage_id")
    private Stage stages;
}
