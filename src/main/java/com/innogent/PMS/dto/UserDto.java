package com.innogent.PMS.dto;

import com.innogent.PMS.entities.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contact;
    private String job;
    private Double salary;
    private Date hiredDate;
    private Role role;
    private String managerEmail;
}
