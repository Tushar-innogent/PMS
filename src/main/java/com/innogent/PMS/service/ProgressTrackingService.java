package com.innogent.PMS.service;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import org.springframework.http.ResponseEntity;

public interface ProgressTrackingService {

   public void addNotesAndRecording(ProgressTracking progressTracking);

   public ResponseEntity<?> getById(long id);

   public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto) throws NoSuchUserExistsException;

   public ResponseEntity<?> getProgressTracking(Integer employeeId) throws NoSuchUserExistsException;
}
