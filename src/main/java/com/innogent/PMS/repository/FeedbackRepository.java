package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    List<Feedback> findByUserId(Integer userId);
}
