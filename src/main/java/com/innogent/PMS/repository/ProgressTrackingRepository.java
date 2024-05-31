package com.innogent.PMS.repository;

import com.innogent.PMS.entities.ProgressTracking;
import com.innogent.PMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressTrackingRepository extends JpaRepository<ProgressTracking,Long> {
    public List<ProgressTracking> findAllByUser(User user);

}
