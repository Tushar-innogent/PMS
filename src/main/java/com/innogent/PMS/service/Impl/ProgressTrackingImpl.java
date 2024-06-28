package com.innogent.PMS.service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.innogent.PMS.config.S3PathParser;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
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

        Optional<ProgressTracking> progressTrackingData = Optional.ofNullable(progressTrackingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Meeting id is not present")));
        //if(progressTrackingData.isPresent())
        //{
        ProgressTrackingDto dto = customMapper.progressEntityToProgressTrackingDto(progressTrackingData.get());
        //if(dto!=null)
        return ResponseEntity.ok(dto);
        //}
        //return ResponseEntity.ok(("Meeting id is not present"));
        //throw new EntityNotFoundException("Meeting id is not present");
    }

    @Override
    public ResponseEntity<?> addProgressTracking(Integer empId, ProgressTrackingDto trackingDto) {
        ProgressTracking progressTracking = customMapper.progressTrackingDtoToEntity(trackingDto);
        User user = userRepository.findById(empId).orElseThrow(null);

        Integer managerId = user.getManagerId();
        Integer manager = null;
        if (managerId != null) {
            User managerUser = userRepository.findById(managerId).orElseThrow(null);
            manager = managerUser.getUserId();
        }
        progressTracking.setUser(user);
        progressTracking.setLineManagerId(manager);
        progressTrackingRepository.save(progressTracking);
        return ResponseEntity.ok(progressTracking);
    }

    @Override
    public ResponseEntity<?> getProgressTracking(Integer employeeId) {
        Optional<User> userOptional = userRepository.findById(employeeId);
        if (userOptional.isPresent()) {
//            String notesFile = notes.getOriginalFilename();
//            String recordingFile = recording.getOriginalFilename();
            List<ProgressTracking> progressTrackingList = progressTrackingRepository.findAllByUser(userOptional.get());
//            long sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000;
//            // Generate pre-signed URL for notes
//            String s3NotesPath = progressTrackingList.get(0).getNotes();
//            S3PathParser s3PathParser = new S3PathParser();
//            s3PathParser.getKey(s3NotesPath);
//            GeneratePresignedUrlRequest generatePresignedUrlRequestNotes = new GeneratePresignedUrlRequest(bucketName, s3NotesPath)
//                    .withMethod(HttpMethod.GET)
//                    .withExpiration(new Date(System.currentTimeMillis() + sevenDaysInMillis)); // 7 days expiration
//            URL notesUrl = client.generatePresignedUrl(generatePresignedUrlRequestNotes);
//
//            // Generate pre-signed URL for recording
//            String s3RecordingPath=progressTrackingList.get(0).getRecording();
//            s3PathParser.getKey(s3RecordingPath);
//            GeneratePresignedUrlRequest generatePresignedUrlRequestRecording = new GeneratePresignedUrlRequest(bucketName,s3RecordingPath )
//                    .withMethod(HttpMethod.GET)
//                    .withExpiration(new Date(System.currentTimeMillis() + sevenDaysInMillis)); // 7 days expiration
//            URL recordingUrl = client.generatePresignedUrl(generatePresignedUrlRequestRecording);

            List<ProgressTrackingDto> dtoList = customMapper.convertListToDto(progressTrackingList);
            //Optional<ProgressTracking> progressTrackingData=progressTrackingRepository.
            return ResponseEntity.ok(dtoList);
        }
        return ResponseEntity.ok("employee id is not found");
    }

    @Override
    public ResponseEntity<?> editProgressTracking(long meetingId, ProgressTrackingDto progressTrackingDto) {
        Optional<ProgressTracking> trackingOpt = progressTrackingRepository.findById(meetingId);
        //ProgressTracking tracking=trackingOpt.get();
        if (trackingOpt.isEmpty()) {
            return ResponseEntity.ok("progresss tracking data is not found");
        }
        ProgressTracking tracking = trackingOpt.get();
        // db s3 path change that
        //trackingOpt.getPath();
// String recordingFile = recording.getOriginalFilename();
       // PutObjectResult putObjectResultRecording = client.putObject(new PutObjectRequest(bucketName, recordingFile, //updated file input st, metaDataRecording));
        tracking.setDate(progressTrackingDto.getDate());
        tracking.setTitle(progressTrackingDto.getTitle());
        tracking.setNotes(progressTrackingDto.getNotes());
        tracking.setRecording(progressTrackingDto.getRecording());
        tracking.setMonth(progressTrackingDto.getMonth());
        tracking.setYear(progressTrackingDto.getYear());
        ProgressTracking savedTracking = progressTrackingRepository.save(tracking);
        ProgressTrackingDto savedTrackingDto = customMapper.progressEntityToProgressTrackingDto(savedTracking);
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
        List<ProgressTracking> getProgressTrackingData = progressTrackingRepository.findAll();
        if (getProgressTrackingData.isEmpty()) {
            return ResponseEntity.ok("unable to find data");
        }
        List<ProgressTrackingDto> progressTrackingDto = customMapper.convertListToDto(getProgressTrackingData);
        return ResponseEntity.ok(progressTrackingDto);
    }


    public ResponseEntity<?> deleteByMeetingId(long id) {
        Optional<ProgressTracking> progressTrackingOpt = progressTrackingRepository.findById(id);
        if (progressTrackingOpt.isPresent()) {
            progressTrackingRepository.deleteById(id);
            return ResponseEntity.ok("Data successfully deleted");
        }
        return ResponseEntity.ok("No related data found");
    }

    @Override
//    public ResponseEntity<?> addNotesAndRecording(Integer empId, String title, String month, String year, @RequestParam MultipartFile notes, @RequestParam MultipartFile recording) throws IOException {
//        String notesFile=notes.getOriginalFilename();
//        String recordingFile= recording.getOriginalFilename();
//
//        ObjectMetadata metaDataNotes=new ObjectMetadata();
//        metaDataNotes.setContentLength(notes.getSize());
//        PutObjectResult putObjectResultNotes=client.putObject(new PutObjectRequest(bucketName,notesFile,notes.getInputStream(),metaDataNotes));
//
//        ObjectMetadata metaDataRecording=new ObjectMetadata();
//        metaDataRecording.setContentLength(recording.getSize());
//        PutObjectResult putObjectResultRecording=client.putObject(new PutObjectRequest(bucketName,recordingFile,recording.getInputStream(),metaDataRecording));
////        $"https://{bucketName}.s3.amazonaws.com/{keyName}";
//        ProgressTracking progressTracking=new ProgressTracking();
//       // progressTracking.setDate(date);
//        progressTracking.setTitle(title);
//        progressTracking.setMonth(month);
//        progressTracking.setYear(year);
//        User user = userRepository.findById(empId).get();
//        progressTracking.setUser(user);
//        progressTracking.setLineManagerId(user.getManagerId());
//        progressTracking.setNotes(filePathBaseUrl+notesFile);
//        progressTracking.setRecording(filePathBaseUrl+recordingFile);
//        ProgressTracking result = progressTrackingRepository.save(progressTracking);
//        ProgressTrackingDto dto = customMapper.progressEntityToProgressTrackingDto(result);
//        dto.setRecording(result.getRecording());
//        dto.setNotes(result.getNotes());
//        return ResponseEntity.ok(dto);
//    }
    public ResponseEntity<?> addNotesAndRecording(Integer empId, String title, String month, String year, @RequestParam MultipartFile notes, @RequestParam MultipartFile recording) throws IOException {
     Optional<ProgressTracking> existMonthAndYear=progressTrackingRepository.findByUser_UserIdAndMonthAndYear(empId,month,year);
     if(existMonthAndYear.isPresent())
     {
         return ResponseEntity.ok(month+" "+year+" is present");
     }
        String notesFile = notes.getOriginalFilename();
        String recordingFile = recording.getOriginalFilename();

        ObjectMetadata metaDataNotes = new ObjectMetadata();
        metaDataNotes.setContentLength(notes.getSize());
        metaDataNotes.setContentType(notes.getContentType());
        PutObjectResult putObjectResultNotes = client.putObject(new PutObjectRequest(bucketName, notesFile, notes.getInputStream(), metaDataNotes));

        ObjectMetadata metaDataRecording = new ObjectMetadata();
        metaDataRecording.setContentLength(recording.getSize());
        metaDataRecording.setContentType(recording.getContentType());
        PutObjectResult putObjectResultRecording = client.putObject(new PutObjectRequest(bucketName, recordingFile, recording.getInputStream(), metaDataRecording));

        // Calculate 7 days in milliseconds
        long sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000;

        // Generate pre-signed URL for notes
        GeneratePresignedUrlRequest generatePresignedUrlRequestNotes = new GeneratePresignedUrlRequest(bucketName, notesFile)
                .withMethod(HttpMethod.GET)
                .withExpiration(new Date(System.currentTimeMillis() + sevenDaysInMillis)); // 7 days expiration
        URL notesUrl = client.generatePresignedUrl(generatePresignedUrlRequestNotes);

        // Generate pre-signed URL for recording
        GeneratePresignedUrlRequest generatePresignedUrlRequestRecording = new GeneratePresignedUrlRequest(bucketName, recordingFile)
                .withMethod(HttpMethod.GET)
                .withExpiration(new Date(System.currentTimeMillis() + sevenDaysInMillis)); // 7 days expiration
        URL recordingUrl = client.generatePresignedUrl(generatePresignedUrlRequestRecording);

        ProgressTracking progressTracking = new ProgressTracking();
        progressTracking.setTitle(title);
        progressTracking.setMonth(month);
        progressTracking.setYear(year);
        User user = userRepository.findById(empId).get();
        progressTracking.setUser(user);
        progressTracking.setLineManagerId(user.getManagerId());
       // progressTracking.setNotes(notesUrl.toString());
        progressTracking.setNotes(filePathBaseUrl+""+notesFile);
        //progressTracking.setRecording(recordingUrl.toString());
        progressTracking.setRecording(filePathBaseUrl+""+recordingFile);
        ProgressTracking result = progressTrackingRepository.save(progressTracking);

        ProgressTrackingDto dto = customMapper.progressEntityToProgressTrackingDto(result);
        dto.setRecording(result.getRecording());
        dto.setNotes(result.getNotes());

        return ResponseEntity.ok(dto);
    }

    @Override
    public Optional<ProgressTracking> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {
        return Optional.empty();
    }

    @Override
    public boolean areNotesUploadedForLastMonth(Long managerId) {
        LocalDate now=LocalDate.now();
        LocalDate startOfLastMonth=now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfLastMonth=now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Optional<User> manager=userRepository.findById(Math.toIntExact(managerId));
        if(manager.isPresent())
        {
            Optional<ProgressTracking> notes=progressTrackingRepository.findByUserAndDateBetween(manager.get(),startOfLastMonth,endOfLastMonth);
            return notes.isPresent();
        }
        return false;
    }
}


//    public URL generatePresignedUrl(String key) {
//        Date expiration = new Date();
//        long expTimeMillis = expiration.getTime();
//        expTimeMillis += 1000 * 60 * 10; // 10 minutes
//        expiration.setTime(expTimeMillis);
//
//        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
//                .withMethod(com.amazonaws.HttpMethod.GET)
//                .withExpiration(expiration);
//        generatePresignedUrlRequest.addRequestParameter("Content-Disposition", "inline");
//
//        return client.generatePresignedUrl(generatePresignedUrlRequest);
//    }
//
//}
