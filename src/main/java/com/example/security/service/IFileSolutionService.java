package com.example.security.service;

import com.example.security.model.FileSolution;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileSolutionService {
    FileSolution findFileById(Long id);

    List<FileSolution> findAllFiles();

    boolean deleteFile(Long id);

    FileSolution saveFile (MultipartFile file, Long userTestId);
}
