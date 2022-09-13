package com.example.security.service.impl;

import com.example.security.model.Meeting;
import com.example.security.repository.MeetingRepository;
import com.example.security.service.IMeetingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService implements IMeetingService {

    private MeetingRepository meetings;

    private MeetingService(MeetingRepository meetings) {
        super();
        this.meetings = meetings;
    }
    @Override
    public Meeting findMeetingById(Long id) {
        try{
            return this.meetings.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Meeting> findAllMeetings() {
        return this.meetings.findAll();
    }

    @Override
    public boolean deleteMeeting(long id) {
        Meeting meeting = this.findMeetingById(id);

        try {
            this.meetings.delete(meeting);
        }
        catch (Exception e){
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        return this.meetings.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Meeting meeting, long id) {
        Meeting oldMeeting = this.findMeetingById(id);

        if (oldMeeting == null) {
            return null;
        } else {
            if (meeting.getMeetingBegin() != null) {
                oldMeeting.setMeetingBegin(meeting.getMeetingBegin());
            }
            if (meeting.getMeetingEnd() !=null) {
                oldMeeting.setMeetingEnd(meeting.getMeetingEnd());
            }
            if (meeting.getTopic() != null) {
                oldMeeting.setTopic(meeting.getTopic());
            }
            if (meeting.getName() != null) {
                oldMeeting.setName(meeting.getName());
            }
            if (meeting.getLocation() != null) {
                oldMeeting.setLocation(meeting.getLocation());
            }
            return this.meetings.save(oldMeeting);
        }
    }

    @Override
    public Long findMeetingByName(String name) {
        return this.meetings.findByName(name).getId();
    }
}


