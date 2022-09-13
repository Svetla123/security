package com.example.security.controller;


import com.example.security.model.Meeting;
import com.example.security.service.IMeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {
    private IMeetingService meetingService;


    public MeetingController(IMeetingService meetingService) {
        super();
        this.meetingService = meetingService;
    }

    @GetMapping("/api/meetings")
    public List<Meeting> findAll () {
        List<Meeting> meetings = this.meetingService.findAllMeetings();
        return meetings;
    }

    @GetMapping("/api/meetings/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        Meeting meeting = this.meetingService.findMeetingById(id);
        if (meeting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting with id " + id + " not found");
        }
        return ResponseEntity.ok(meeting);
    }

    @PostMapping("/api/meetings")
    public ResponseEntity<?> create (@RequestBody Meeting meeting) {
       Meeting newMeeting = this.meetingService.createMeeting(meeting);
        return ResponseEntity.ok(newMeeting);
    }

    @DeleteMapping("/api/meetings/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.meetingService.deleteMeeting(id);

        if (result) {
            return ResponseEntity.ok("Meeting with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting with id " + id + " not found");
        }
    }

    @PutMapping("/api/meetings/{id}")
    public ResponseEntity<?> update (@RequestBody Meeting meeting, @PathVariable Long id) {
        Meeting updatedMeeting = this.meetingService.updateMeeting(meeting, id);
        if (updatedMeeting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedMeeting);
    }
}
