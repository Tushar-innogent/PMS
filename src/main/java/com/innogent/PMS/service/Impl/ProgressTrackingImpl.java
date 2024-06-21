package com.innogent.PMS.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.innogent.PMS.dto.ProgressTrackingDto;
import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.mapper.CustomMapper;
import com.innogent.PMS.repository.ProgressTrackingRepository;
import com.innogent.PMS.repository.UserRepository;
import com.innogent.PMS.service.ProgressTrackingService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressTrackingImpl implements ProgressTrackingService {
    private static final Logger log = LoggerFactory.getLogger(ProgressTrackingImpl.class);
    private static final String filePathBaseUrl = "https://performance-management-system-bucket.s3.amazonaws.com/";
    @Autowired
    ProgressTrackingRepository progressTrackingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomMapper customMapper;

    @Autowired
    private AmazonS3 client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public void addNotesAndRecording(ProgressTracking progressTracking) {
        progressTrackingRepository.save(progressTracking);
    }
    @Override
    public ResponseEntity<?> getById(long id) throws EntityNotFoundException {

        Optional<ProgressTracking> progressTrackingData= Optional.ofNullable(progressTrackingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Meeting id is not present")));
        //if(progressTrackingData.isPresent())
        //{
           ProgressTrackingDto dto= customMapper.progressEntityToProgressTrackingDto(progressTrackingData.get());
            //if(dto!=null)
                return ResponseEntity.ok(dto);
        //}
        //return ResponseEntity.ok(("Meeting id is not present"));
        //throw new EntityNotFoundException("Meeting id is not present");
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

        tracking.setDate(progressTrackingDto.getDate());
        tracking.setTitle(progressTrackingDto.getTitle());
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

    @Override
    public ResponseEntity<?> addNotesAndRecording(Integer empId,LocalDate date, String title, MultipartFile notes, MultipartFile recording) throws IOException {

        String notesFile=notes.getOriginalFilename();
        String recordingFile= recording.getOriginalFilename();

        ObjectMetadata metaDataNotes=new ObjectMetadata();
        metaDataNotes.setContentLength(notes.getSize());
        PutObjectResult putObjectResultNotes=client.putObject(new PutObjectRequest(bucketName,notesFile,notes.getInputStream(),metaDataNotes));

        ObjectMetadata metaDataRecording=new ObjectMetadata();
        metaDataRecording.setContentLength(recording.getSize());
        PutObjectResult putObjectResultRecording=client.putObject(new PutObjectRequest(bucketName,recordingFile,recording.getInputStream(),metaDataRecording));
//        $"https://{bucketName}.s3.amazonaws.com/{keyName}";
        ProgressTracking progressTracking=new ProgressTracking();
        progressTracking.setDate(date);
        progressTracking.setTitle(title);
        User user = userRepository.findById(empId).get();
        progressTracking.setUser(user);
        progressTracking.setLineManagerId(user.getManagerId());
        progressTracking.setNotes(filePathBaseUrl+notesFile);
        progressTracking.setRecording(filePathBaseUrl+recordingFile);
        ProgressTracking result = progressTrackingRepository.save(progressTracking);
        ProgressTrackingDto dto = customMapper.progressEntityToProgressTrackingDto(result);
        dto.setRecording(result.getRecording());
        dto.setNotes(result.getNotes());
        return ResponseEntity.ok(dto);
    }


}
