package com.example.TestParserExecutor.services.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public interface UploadService {
    boolean saveFile(MultipartFile file, String filePath);
}
