package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressTrackingImpl implements ProgressTrackingService {

    @Autowired
    ProgressTrackingRepository progressTrackingRepository;


    @Override
    public void addNotesAndRecording(ProgressTracking progressTracking) {
        progressTrackingRepository.save(progressTracking);
    }

    @Override
    public ResponseEntity<?> getById(long id) {
        Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.findById(id);
        if(!progressTrackingData.isEmpty())
        {
            return ResponseEntity.ok(progressTrackingData);
        }
        return ResponseEntity.ok("Id is not present");
    }


}