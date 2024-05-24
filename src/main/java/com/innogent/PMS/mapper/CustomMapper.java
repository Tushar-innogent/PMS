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

    public Goal goalDtoToEntity(GoalDto goalDto){
        return modelMapper.map(goalDto, Goal.class);
    }
    public GoalDto goalEntityToGoalDto(Goal goal){
        return modelMapper.map(goal, GoalDto.class);
    }

    public User userDtoToEntity (UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    public List<GoalDto> goalListToGoalDto(List<Goal> goalsList) {
        return goalsList.stream().map(this::goalEntityToGoalDto).toList();
    }
}
