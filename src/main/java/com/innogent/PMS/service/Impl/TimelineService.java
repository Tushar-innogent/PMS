package com.innogent.PMS.service.Impl;

import com.innogent.PMS.entities.Timeline;
import com.innogent.PMS.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimelineService implements com.innogent.PMS.service.TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;
    @Override
    public Timeline addTimeline(Timeline tm) {
        if(timelineRepository.existsByTimelineType(tm.getTimelineType())){
            return null;
        }
        return timelineRepository.save(tm);
    }

    @Override
    public Timeline editTimeline(Integer timelineId, Timeline tm) {
        Optional<Timeline> existingTimeline = timelineRepository.findById(timelineId);
        if(existingTimeline.isPresent()){
            tm.setTimelineId(existingTimeline.get().getTimelineId());
            return timelineRepository.save(tm);
        }
        return null;
    }

    @Override
    public String deleteTimeline(Integer timelineId) {
        if(timelineRepository.existsById(timelineId)){
            timelineRepository.deleteById(timelineId);
            return "Record Deleted!";
        }
        return "Record doesn't exists!";
    }

    @Override
    public List<Timeline> listAllTimelines() {
        return timelineRepository.findAll();
    }
}
