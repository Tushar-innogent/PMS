package com.innogent.PMS.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contact;
    private String job;
    private Double salary;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @Temporal(TemporalType.DATE)
//    private String hiredDate;
//
//    @ManyToOne
//    @JoinColumn(name = "manager_id")
//    private Employee manager;
}
