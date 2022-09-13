package com.example.security.service;


import com.example.security.model.Meeting;

import java.util.List;

public interface IMeetingService {
    Meeting findMeetingById(Long id);
    List<Meeting> findAllMeetings();

    boolean deleteMeeting(long id);

    Meeting createMeeting(Meeting meeting);

    Meeting updateMeeting(Meeting meeting, long id);

    Long findMeetingByName(String name);
}
