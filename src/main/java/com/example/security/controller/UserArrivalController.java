package com.example.security.controller;

import com.example.security.model.UserArrival;
import com.example.security.service.IAppUserSerivce;
import com.example.security.service.IMeetingService;
import com.example.security.service.IUserArrivalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserArrivalController {
    private IUserArrivalService userArrivalService;
    private IAppUserSerivce appUserSerivce;

    private IMeetingService meetingService;
    public UserArrivalController(IUserArrivalService userArrivalService, IAppUserSerivce appUserSerivce, IMeetingService meetingService) {
        super();
        this.userArrivalService = userArrivalService;
        this.appUserSerivce = appUserSerivce;
        this.meetingService = meetingService;
    }
    @GetMapping("/api/userarrivals")
    public List<UserArrival> findAll () {
        List<UserArrival> userArrivals = this.userArrivalService.findAllUserArrivals();
        return userArrivals;
    }
    @GetMapping("/api/userarrivals/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        UserArrival userArrival = this.userArrivalService.findUserArrivalById(id);
        if (userArrival == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserArrival with id " + id + " not found");
        }
        return ResponseEntity.ok(userArrival);
    }

    @DeleteMapping ("/api/userarrivals{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.userArrivalService.deleteUserArrival(id);
        if (result) {
            return ResponseEntity.ok("UserArrival with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserArrival with id " + id + " not found");
        }
    }

    @PostMapping("/api/userarrivals")
    public ResponseEntity<?> create (@RequestBody UserArrival userArrival) {
//        userArrival.getUser().setId(userService.findUserByUsername(userArrival.getUser().getUsername()));

//        userArrival.getMeeting().setId((meetingService.findMeetingByName(userArrival.getMeeting().getName())));

        UserArrival newUserArrival = this.userArrivalService.createUserArrival(userArrival);
        return ResponseEntity.ok(newUserArrival);
    }

    @PutMapping("/api/userarrivals/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody UserArrival userArrival) {
        UserArrival updatedUserArrival = this.userArrivalService.updateUserArrival(id, userArrival);
        if (updatedUserArrival == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserArrival with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedUserArrival);
    }
}
