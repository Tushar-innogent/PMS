package com.innogent.PMS.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.innogent.PMS.enums.StageName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="stages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stageId;
    @Enumerated(EnumType.STRING)
    @Column(length = 50,unique = true, nullable = false)
    private StageName stageName;
    private String description;
    private Boolean isActive;

    @OneToOne(mappedBy = "stages", cascade = CascadeType.ALL)
    @JsonBackReference
    private Timeline timeline;
}
