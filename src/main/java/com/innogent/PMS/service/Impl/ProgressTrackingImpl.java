package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressTrackingImpl implements ProgressTrackingService {
    @Autowired
    ProgressTrackingRepository progressTrackingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomMapper customMapper;
    @Override
    public void addNotesAndRecording(ProgressTracking progressTracking) {
        progressTrackingRepository.save(progressTracking);
    }
    @Override
    public ResponseEntity<?> getById(long id) {

        Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.findById(id);
        if(progressTrackingData.isPresent())
        {
           ProgressTrackingDto dto= customMapper.progressEntityToProgressTrackingDto(progressTrackingData.get());
            if(dto!=null)
                return ResponseEntity.ok(dto);
        }
        return ResponseEntity.ok("Meeting id is not present");
    }

    @Override
    public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto) {
        ProgressTracking progressTracking=customMapper.progressTrackingDtoToEntity(trackingDto);
        User user=userRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee id is not found"));

       Integer managerId= user.getManagerId();
       Integer manager=null;
       if(managerId!=null)
       {
           User managerUser=userRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Manager id is not found"));
           manager = managerUser.getUserId();
       }
        progressTracking.setUser(user);
       progressTracking.setLineManagerId(manager);
       progressTrackingRepository.save(progressTracking);
       return ResponseEntity.ok(progressTracking);
    }

    @Override
    public ResponseEntity<?> getProgressTracking(Integer employeeId) {
        Optional<User> userOptional=userRepository.findById(employeeId);
        if (userOptional.isPresent())
        {
            List<ProgressTracking> progressTrackingList=progressTrackingRepository.findAllByUser(userOptional.get());
            List<ProgressTrackingDto> dtoList=customMapper.convertListToDto(progressTrackingList);
            //Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.
            return ResponseEntity.ok(dtoList);
        }
        return ResponseEntity.ok("employee id is not found");
    }

}
