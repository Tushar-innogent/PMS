package com.innogent.PMS.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 100)
    private String password;
    @Column(length = 15)
    private String contact;
    @Column(length = 50)
    private String job;
    private Double salary;
    private Date hiredDate;
    private Integer managerId;

    @ManyToOne(cascade= CascadeType.MERGE, targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;
}
