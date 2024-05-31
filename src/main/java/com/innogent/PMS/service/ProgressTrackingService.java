package com.innogent.PMS.service;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProgressTrackingService {

  public void addNotesAndRecording(ProgressTracking progressTracking);

   public ResponseEntity<?> getById(long id);


   public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto);

   public ResponseEntity<?> getProgressTracking(Integer employeeId);

   public ResponseEntity<?> editProgressTracking(Long meetingId, ProgressTrackingDto progressTrackingDto);

   public ResponseEntity<?> getAllData();

    public ResponseEntity<?> deleteByMeetingId(long id);
}
