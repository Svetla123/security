package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "profileImages")
public class ProfileImage extends AppModel{

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long Id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

    @OneToOne
    @JsonIgnore
    private UserProfile userProfile;

    public ProfileImage() {}

    public ProfileImage(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
