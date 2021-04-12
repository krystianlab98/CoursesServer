package com.github.course.features.file;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {


    private Path fileStoragePath;

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    public FileService(@Value("${file.storage.location}") String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
    }

    public String storeFile(MultipartFile file) {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        fileStoragePath = this.createDirecotries();
        Path filePath = Paths.get(fileStoragePath + "\\" + filename);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file");
        }
        return filename;
    }

    public String storeFile(MultipartFile file, String path) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        fileStoragePath = this.createDirecotries(path);

        Path filePath = Paths.get(fileStoragePath + "\\" + filename);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file");
        }
        return filePath.toString();
    }

    public Resource getSingleFile(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file");
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or is not readable");
        }
    }

    private Path createDirecotries(String path) {
        fileStoragePath = Paths.get(fileStorageLocation + "\\" + path).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStoragePath;
    }

    private Path createDirecotries() {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
        return fileStoragePath;
    }
}
