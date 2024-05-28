package com.innogent.PMS.service;

import com.innogent.PMS.entities.Timeline;

import java.util.List;

public interface TimelineService {
    //add timeline
    public Timeline addTimeline(Timeline tm);
    //edit timeline
    public Timeline editTimeline(Integer timelineId, Timeline tm);
    //delete timeline
    public String deleteTimeline(Integer timelineId);
    //get all
    public List<Timeline> listAllTimelines();
}
