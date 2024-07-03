package com.innogent.PMS.repository;

import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgressTrackingRepository extends JpaRepository<ProgressTracking,Long> {
    public List<ProgressTracking> findAllByUser(User user);
    Optional<ProgressTracking> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    //Optional<ProgressTracking> findByUser_UserIdAndMonthAndYear(Integer userId,String month,String year);
    Optional<ProgressTracking> findByUserAndMonthAndYear(User user, String month, String year);
}
