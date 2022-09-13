package com.example.security.controller;


import com.example.security.model.FileSolution;
import com.example.security.model.Response;
import com.example.security.service.IFileSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileSolutionController {
    @Autowired
    private IFileSolutionService fileSolutionService;
    public FileSolutionController(IFileSolutionService fileSolutionService) {
        super();
        this.fileSolutionService = fileSolutionService;
    }

    @GetMapping("/api/fileSolutions")
    public List<FileSolution> findAll() {
        List <FileSolution> fileSolutions = this.fileSolutionService.findAllFiles();
        return fileSolutions;
    }

    @GetMapping("/api/fileSolutions/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        FileSolution fileSolution = this.fileSolutionService.findFileById(id);
        if (fileSolution == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with id " + id + " not found");
        }
        return ResponseEntity.ok(fileSolution);
    }

    @DeleteMapping("/api/fileSolutions/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.fileSolutionService.deleteFile(id);
        if (result) {
            return ResponseEntity.ok("File with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with id " + id + " not found");
        }
    }

    @PostMapping("/api/fileSolutions")
    public Response create(@RequestParam("file") MultipartFile file, @RequestParam("userTest") Long userTestId) {
        System.out.println(userTestId);
        FileSolution fileSolution = fileSolutionService.saveFile(file,userTestId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/userTestDownload/")
                .path(fileSolution.getFileName())
                .toUriString();


        return new Response(fileSolution.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/api/multipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("userTest") Long userTestId) {
        return Arrays.asList(files)
                .stream()
                .map(file -> create(file, userTestId))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/fileDownload/{fileName:.+}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileName, HttpServletRequest request) {
        // Load file as Resource
        FileSolution fileSolution = fileSolutionService.findFileById(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileSolution.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileSolution.getFileName() + "\"")
                .body(new ByteArrayResource(fileSolution.getCompiledSolution()));
    }
}
