package com.innogent.PMS.entities;

import com.innogent.PMS.enums.GoalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="goals")
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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "goals", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stage> stages = new ArrayList<>();
}
