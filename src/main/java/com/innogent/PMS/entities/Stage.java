package com.innogent.PMS.entities;

import com.innogent.PMS.enums.StageName;
import com.innogent.PMS.enums.StageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    @JoinColumn(name = "goalId", nullable = false)
    private Goal goals;

    public Stage(StageName stageName, StageStatus stageStatus, Goal goals) {
        this.stageName = stageName;
        this.stageStatus = stageStatus;
        this.goals = goals;
    }
}
