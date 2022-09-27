package com.example.TestParserExecutor.services.github;

import org.springframework.stereotype.Service;


@Service
public interface GithubService {
    boolean createRepo(String repoName);

    boolean pushCode(String filePath, String repoName);

    boolean updateCode(String filePath, String repoName);

    String getGithubLink();
}
