package com.innogent.PMS.entities;

import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.enums.StageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="stages")
@Getter
@Setter
@NoArgsConstructor
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stageId;
    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StageName stageName;
    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private StageStatus stageStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineId", nullable = false)
    private Timeline timeline;

    public Stage(StageName stageName, StageStatus stageStatus, Timeline timeline) {
        this.stageName = stageName;
        this.stageStatus = stageStatus;
        this.timeline = timeline;
    }
}
