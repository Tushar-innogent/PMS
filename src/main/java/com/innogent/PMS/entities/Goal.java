package com.innogent.PMS.entities;

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
    @Column(length = 50)
    private GoalType goalType;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
}
