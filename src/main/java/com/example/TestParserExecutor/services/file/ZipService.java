package com.example.TestParserExecutor.services.file;

import org.springframework.stereotype.Service;


@Service
public interface ZipService {
    boolean unzipFile(String zipFileDestination, String destination);
}
