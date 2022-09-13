package com.example.security.repository;

import com.example.security.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findByName(String name);
}
