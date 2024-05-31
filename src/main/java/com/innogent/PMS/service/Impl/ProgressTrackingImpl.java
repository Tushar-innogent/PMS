package com.innogent.PMS.service.Impl;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto)  {
        ProgressTracking progressTracking=customMapper.progressTrackingDtoToEntity(trackingDto);
        User user=userRepository.findById(empId).orElseThrow(null);

       Integer managerId= user.getManagerId();
       Integer manager=null;
       if(managerId!=null)
       {
           User managerUser=userRepository.findById(managerId).orElseThrow(null);
           manager = managerUser.getUserId();
       }
       progressTracking.setUser(user);
       progressTracking.setLineManagerId(manager);
       progressTrackingRepository.save(progressTracking);
       return ResponseEntity.ok(progressTracking);
    }

    @Override
    public ResponseEntity<?> getProgressTracking(Integer employeeId)  {
        Optional<User> userOptional= userRepository.findById(employeeId);
        if (userOptional.isPresent())
        {
            List<ProgressTracking> progressTrackingList=progressTrackingRepository.findAllByUser(userOptional.get());
            List<ProgressTrackingDto> dtoList=customMapper.convertListToDto(progressTrackingList);
            //Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.
            return ResponseEntity.ok(dtoList);
        }
        return ResponseEntity.ok("employee id is not found");
    }

    @Override
    public ResponseEntity<?> editProgressTracking(long meetingId, ProgressTrackingDto progressTrackingDto) {
        Optional<ProgressTracking> trackingOpt=progressTrackingRepository.findById(meetingId);
        //ProgressTracking tracking=trackingOpt.get();
        if(trackingOpt.isEmpty())
        {
            return ResponseEntity.ok("progresss tracking data is not found");
        }
        ProgressTracking tracking=trackingOpt.get();
        tracking.setMeetingId(meetingId);
        tracking.setNotes(progressTrackingDto.getNotes());
        tracking.setRecording(progressTrackingDto.getRecording());
        ProgressTracking savedTracking=progressTrackingRepository.save(tracking);
        ProgressTrackingDto savedTrackingDto=customMapper.progressEntityToProgressTrackingDto(savedTracking);
        return ResponseEntity.ok(savedTrackingDto);
    }


//    public ResponseEntity<?> editProgressTracking(Long meetingId, ProgressTrackingDto progressTrackingDto) {
//        Optional<ProgressTracking> trackingOpt=progressTrackingRepository.findById(meetingId);
//        //ProgressTracking tracking=trackingOpt.get();
//        if(trackingOpt.isEmpty())
//        {
//            return ResponseEntity.ok("progresss tracking data is not found");
//        }
//        ProgressTracking tracking=trackingOpt.get();
//        tracking.setMeetingId(meetingId);
//        tracking.setNotes(progressTrackingDto.getNotes());
//        tracking.setRecording(progressTrackingDto.getRecording());
//        ProgressTracking savedTracking=progressTrackingRepository.save(tracking);
//        ProgressTrackingDto savedTrackingDto=customMapper.progressEntityToProgressTrackingDto(savedTracking);
//        return ResponseEntity.ok(savedTrackingDto);
//    }


    public ResponseEntity<?> getAllData() {
        List<ProgressTracking> getProgressTrackingData=progressTrackingRepository.findAll();
        if(getProgressTrackingData.isEmpty())
        {
            return ResponseEntity.ok("unable to find data");
        }
        List<ProgressTrackingDto> progressTrackingDto=customMapper.convertListToDto(getProgressTrackingData);
         return ResponseEntity.ok(progressTrackingDto);
    }


    public ResponseEntity<?> deleteByMeetingId(long id) {
        Optional<ProgressTracking> progressTrackingOpt=progressTrackingRepository.findById(id);
        if(progressTrackingOpt.isPresent())
        {
            progressTrackingRepository.deleteById(id);
            return ResponseEntity.ok("Data successfully deleted");
        }
        return ResponseEntity.ok("No related data found");
    }


}
