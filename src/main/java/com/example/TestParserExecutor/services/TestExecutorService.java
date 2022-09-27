package com.example.TestParserExecutor.services;

import com.example.TestParserExecutor.model.BuildResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface TestExecutorService {
    BuildResult saveFileAndCreateTestPipeline(MultipartFile multipartFile) throws RuntimeException;

    BuildResult saveFileAndCreateTestMaven(MultipartFile multipartFile) throws RuntimeException;
}
