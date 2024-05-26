package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressTrackingImpl implements ProgressTrackingService {
    @Autowired
    ProgressTrackingRepository progressTrackingRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void addNotesAndRecording(ProgressTracking progressTracking) {
        progressTrackingRepository.save(progressTracking);
    }
    @Override
    public ResponseEntity<?> getById(long id) {
        Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.findById(id);
        if(progressTrackingData.isPresent())
        {
            return ResponseEntity.ok(progressTrackingData.get());
        }
        return ResponseEntity.ok("Meeting id is not present");
    }

    @Override
    public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTracking tracking) {
       User user=userRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee id is not found"));

       Integer managerId= user.getManagerId();
       User manager=null;
       if(managerId!=null)
       {
           manager=userRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Manager id is not found"));
       }
       tracking.setUser(user);
       tracking.setLineManager(manager);
       progressTrackingRepository.save(tracking);
       return ResponseEntity.ok(tracking);
    }

    @Override
    public ResponseEntity<?> getProgressTracking(Integer employeeId) {
         Optional<User> userOptional=userRepository.findById(employeeId);
         if (userOptional.isPresent())
         {
            List<ProgressTracking> progressTrackingList=progressTrackingRepository.findAllByUser(userOptional.get());
            //Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.
             return ResponseEntity.ok(progressTrackingList);
         }
         return ResponseEntity.ok("employee id is not found");
    }


}
