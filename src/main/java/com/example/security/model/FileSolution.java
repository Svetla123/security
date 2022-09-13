package com.example.security.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
public class FileSolution extends AppModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] compiledSolution;

    @ManyToOne
    @JoinColumn(name = "userTest_id")
    private UserTest userTest;

    public FileSolution() {}
    public FileSolution(String fileName, String fileType, byte[] data, UserTest userTest) {
        this.fileName = fileName;
        this.fileType  = fileType;
        this.compiledSolution = data;
        this.userTest = userTest;
    }


}
