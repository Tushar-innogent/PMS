package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Timeline;
import com.innogent.PMS.enums.TimelineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    public boolean existsByTimelineType(TimelineType type);
}