package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "meetings")
@Data
public class Meeting extends AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long Id;

    @Column (nullable = false)
    private String name;
    private String location;
    private LocalDateTime meetingBegin;

    private LocalDateTime meetingEnd;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany (mappedBy = "meeting")
    @JsonIgnore
    private List<UserArrival> userArrivals;

    @OneToMany(mappedBy = "meeting")
    @JsonIgnore
    private List<Comment> comments;
}
