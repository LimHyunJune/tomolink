package com.example.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileUploadUtil {

    @Value("${file.path}")
    String filePath;
    public String getFullPath(String filename) {
        return filePath + filename;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if(file.isEmpty())
            return null;

        String originalFileName = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        file.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf('.');
        return originalFileName.substring(pos + 1);
    }
}
