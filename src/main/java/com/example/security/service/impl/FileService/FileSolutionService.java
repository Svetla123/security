package com.example.security.service.impl.FileService;

import com.example.security.model.FileSolution;
import com.example.security.model.UserTest;
import com.example.security.repository.FileSolutionRepository;
import com.example.security.service.IFileSolutionService;
import com.example.security.service.impl.UserTestService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class FileSolutionService implements IFileSolutionService {
    private FileSolutionRepository files;
    private UserTestService userTestService;

    public FileSolutionService(FileSolutionRepository files, UserTestService userTestService) {
        super();
        this.userTestService = userTestService;
        this.files = files;
    }

    @Override
    public FileSolution findFileById(Long id) {
        try {
            return this.files.findById(id).get();
        }
        catch (Exception e) {
            return null;
        }
    }
    @Override
    public List<FileSolution> findAllFiles() {
        return this.files.findAll();
    }

    @Override
    public boolean deleteFile(Long id) {
        try {
            this.files.deleteById(id);
        }
        catch (Exception e) {
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public FileSolution saveFile (MultipartFile file, Long userTestId) {
        // Normalize file name
        System.out.println(userTestId);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserTest userTest = this.userTestService.findUserTestById(userTestId);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //mozda bolje sa setCompiledSolution(file.getBytes());
            FileSolution newFile = new FileSolution(fileName,
                    file.getContentType(),
                    file.getBytes(),
                    userTest);

            return files.save(newFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
