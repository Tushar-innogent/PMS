package com.innogent.PMS.repository;

import com.innogent.PMS.dto.FeedbackRequestDto;
import com.innogent.PMS.entities.Feedback;
import com.innogent.PMS.entities.FeedbackRequest;
import com.innogent.PMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRequestRepository  extends JpaRepository<FeedbackRequest,Long> {
    public List<FeedbackRequest> findAllByFeedbackSeeker(User user);
}
