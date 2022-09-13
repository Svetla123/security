package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "userProfiles")
public class UserProfile extends AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long Id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String mobile;
    private String faculty;
    @Column(nullable = false)
    private String residence;
    private String bio;
    private String gender;


    @ManyToOne
    @JoinColumn(name = "educationLevel_id")
    private EducationLevel educationLevel;

    @OneToOne (mappedBy = "userProfile")
    @JsonIgnore
    private AppUser user;

    @OneToOne
    @JoinColumn(name = "profileImage_id")
    private ProfileImage profileImage;
}
