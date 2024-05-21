package com.innogent.PMS.entities;

import com.innogent.PMS.enums.GoalStatus;
import com.innogent.PMS.enums.GoalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    @Enumerated(EnumType.STRING)
    private GoalType goalType;
    private String description;
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;
}
