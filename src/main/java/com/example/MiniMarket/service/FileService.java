package com.example.MiniMarket.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
@Service
public class FileService {

    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File kosong");
        }
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

}

