package com.dcims.backend.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".pdf", ".jpg", ".jpeg", ".png");
    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "application/pdf", "image/jpeg", "image/png"
    );

    public String storeApprovalLetter(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Approval letter is required");
        }

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName).toLowerCase();

        boolean extOk = ALLOWED_EXTENSIONS.contains(extension);
        boolean mimeOk = ALLOWED_MIME_TYPES.contains(file.getContentType());

        if (!extOk || !mimeOk) {
            throw new IllegalArgumentException("Only PDF, JPG, and PNG files are allowed for the approval letter");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueName = System.currentTimeMillis() + "-" + UUID.randomUUID() + extension;
        Path destination = uploadPath.resolve(uniqueName);
        file.transferTo(destination);

        return uniqueName;
    }

    public Path resolveApprovalLetter(String storedName) {
        Path base = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path file = base.resolve(storedName).normalize();
        if (!file.startsWith(base)) {
            throw new IllegalArgumentException("Invalid file path");
        }
        return file;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
