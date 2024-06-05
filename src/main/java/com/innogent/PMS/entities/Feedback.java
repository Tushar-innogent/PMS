package com.innogent.PMS.entities;

import com.innogent.PMS.enums.EvaluationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type")
    private EvaluationType feedbackType;

    private Integer rating;
    private LocalDateTime feedbackDate;

    @Column(columnDefinition = "TEXT")
    private String comments;
}
