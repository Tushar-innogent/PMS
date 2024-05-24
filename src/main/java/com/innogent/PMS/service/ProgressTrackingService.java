package com.innogent.PMS.service;

import com.innogent.PMS.entities.ProgressTracking;
import org.springframework.http.ResponseEntity;

public interface ProgressTrackingService {

  public void addNotesAndRecording(ProgressTracking progressTracking);

   public ResponseEntity<?> getById(long id);


   public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTracking tracking);

   public ResponseEntity<?> getProgressTracking(Integer employeeId);
}
