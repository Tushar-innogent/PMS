package com.innogent.PMS.entities;

import com.innogent.PMS.enums.GoalStatus;
import com.innogent.PMS.enums.GoalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(length = 100)
    private String goalName;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private GoalType goalType;
    @Column(columnDefinition = "TEXT")

    private String description;
    @CreationTimestamp
    private LocalDateTime setDate;
    @Column(columnDefinition = "TEXT")
    private String measurable;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private GoalStatus goalStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        this.isDeleted = false;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
}
