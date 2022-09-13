package com.example.security.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "users_arrivals")
public class UserArrival extends AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long Id;
    private boolean arrivalApproved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

}
