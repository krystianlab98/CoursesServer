package com.github.course.features.file;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/single/upload")
    public FileUploadResponseDto singleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = fileService.storeFile(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();
        String contentType = file.getContentType();
        FileUploadResponseDto response = new FileUploadResponseDto(fileName, contentType, url);

        return response;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> getSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.getSingleFile(fileName);

        String contentType;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                // to download file instead of render
//                .header(HttpHeaders.CONTENT_DISPOSITION, "aatttachment;fileName="+resource.getFilename())
                .body(resource);
    }


}
