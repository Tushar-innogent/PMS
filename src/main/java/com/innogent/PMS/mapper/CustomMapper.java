package com.innogent.PMS.mapper;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.List;

@Component
public class CustomMapper {

    private ModelMapper modelMapper = new ModelMapper();

    //************ goal module mapper methods
    public Goal goalDtoToEntity(GoalDto goalDto){
        return modelMapper.map(goalDto, Goal.class);
    }
    public GoalDto goalEntityToGoalDto(Goal goal){
        return modelMapper.map(goal, GoalDto.class);
    }
    //to convert list of goals to list of goalDto
    public List<GoalDto> goalListToGoalDto(List<Goal> goalsList) {
        return goalsList.stream().map(this::goalEntityToGoalDto).toList();
    }

    //************ user module mapper methods
    public User userDtoToEntity (UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setRole(userDto.getRole());
        return user;
    }
    public UserDto userEntityToDto (User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole());
        return userDto;
    }
}
