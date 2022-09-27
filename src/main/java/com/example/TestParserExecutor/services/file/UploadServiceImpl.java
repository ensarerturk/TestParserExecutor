package com.example.TestParserExecutor.services.file;

import com.example.TestParserExecutor.services.github.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    ZipService zipService;
    @Autowired
    GithubService githubService;

    @Override
    public boolean saveFile(MultipartFile file, String filePath) {
        try (InputStream in = file.getInputStream()) {
            // Copies Spring's multipartfile inputStream to /sismed/temp/exames (absolute path)
            Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
