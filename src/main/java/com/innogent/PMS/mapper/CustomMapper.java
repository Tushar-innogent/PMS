package com.innogent.PMS.mapper;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.entities.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class CustomMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Goal goalDtoToEntity(GoalDto goalDto){
        return modelMapper.map(goalDto, Goal.class);
    }
    public GoalDto goalDtoToEntity(Goal goal){
        return modelMapper.map(goal, GoalDto.class);
    }
}
