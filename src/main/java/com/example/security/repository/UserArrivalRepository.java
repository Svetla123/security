package com.example.security.repository;

import com.example.security.model.UserArrival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArrivalRepository extends JpaRepository<UserArrival, Long> {
}
