package com.innogent.PMS.service;

import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public interface ProgressTrackingService {

  public void addNotesAndRecording(ProgressTracking progressTracking);

   public ResponseEntity<?> getById(long id);


   public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto);

   public ResponseEntity<?> getProgressTracking(Integer employeeId);


    public ResponseEntity<?> editProgressTracking(long meetingId, ProgressTrackingDto progressTrackingDto);

    public ResponseEntity<?> getAllData();

   public ResponseEntity<?> deleteByMeetingId(long id);

   public ResponseEntity<?> addNotesAndRecording(Integer empId, String title,String month,String year, MultipartFile notes, MultipartFile recording) throws IOException;

 //for checking that notes and recording uploaded or not
 Optional<ProgressTracking> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
 public

   boolean areNotesUploadedForLastMonth(Long managerId);
}
