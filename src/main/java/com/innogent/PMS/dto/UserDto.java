package com.innogent.PMS.dto;

import com.innogent.PMS.entities.Role;
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
    private Date hiredDate;
    private String managerEmail;
    private Role role;
}
