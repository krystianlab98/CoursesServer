package com.github.course.features.file;


import com.github.course.features.lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    FileService fileService;
    LessonService lessonService;

    @Autowired
    public FileController(FileService fileService, LessonService lessonService) {
        this.fileService = fileService;
        this.lessonService = lessonService;
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

    @PostMapping("/folder/upload")
    public FileUploadResponseDto folderFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileService.storeFile(file, "backendprograming\\JavaCourse\\title");
        String url = fileService.getUrl(fileName);
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

    @GetMapping("/lessons/video/{fileId}/{fileName}")
    public ResponseEntity<Resource> getSingleFile(@PathVariable Long fileId, @PathVariable String fileName, HttpServletRequest request) {

        String filePath = lessonService.findPathByLesson(fileId);


        Resource resource = fileService.getSingleFile(fileName, filePath);

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
