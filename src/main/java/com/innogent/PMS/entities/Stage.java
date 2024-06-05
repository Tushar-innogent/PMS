package com.innogent.PMS.entities;

import com.innogent.PMS.enums.StageName;
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
    private String description;
    private Boolean isActive;

    @OneToOne(mappedBy = "stages", cascade = CascadeType.ALL)
    private Timeline timeline;

    public Stage(StageName stageName, Timeline timeline) {
        this.stageName = stageName;
        this.timeline = timeline;
    }
}
