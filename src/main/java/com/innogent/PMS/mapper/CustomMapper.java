package com.innogent.PMS.mapper;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class CustomMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public Goal goalDtoToEntity(GoalDto goalDto){
        return modelMapper.map(goalDto, Goal.class);
    }
    public GoalDto goalDtoToEntity(Goal goal){
        return modelMapper.map(goal, GoalDto.class);
    }

    public User userDtoToEntity (UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
}
