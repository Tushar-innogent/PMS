package com.innogent.PMS.entities;

import com.innogent.PMS.enums.TimelineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "timelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class

Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer timelineId;
    private TimelineType timelineType;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "timeline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Stage> stages = new HashSet<>();
}
