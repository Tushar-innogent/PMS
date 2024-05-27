package com.innogent.PMS.mapper;

import com.innogent.PMS.dto.GoalDto;
import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.dto.UserDto;
import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.ProgressTracking;
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
    //progress tracking module mapper methods
    public ProgressTracking progressTrackingDtoToEntity(ProgressTrackingDto progressTrackingDto)
    {
        return modelMapper.map(progressTrackingDto, ProgressTracking.class);
    }

    public ProgressTrackingDto  progressEntityToProgressTrackingDto(ProgressTracking progressTracking)
    {
        ProgressTrackingDto dto = new ProgressTrackingDto();
        dto.setMeetingId(progressTracking.getMeetingId());
        dto.setUserId(progressTracking.getUser().getUserId());
        dto.setDate(progressTracking.getDate());
        dto.setNotes(progressTracking.getNotes());
        dto.setRecording(progressTracking.getRecording());
        dto.setManagerId(progressTracking.getUser().getManagerId());
        return dto;
    }
    //convert list to dto
    public List<ProgressTrackingDto> convertListToDto(List<ProgressTracking> entityList)
    {
        return entityList.stream()
                .map(this::progressEntityToProgressTrackingDto)
                .toList();

    }
}
