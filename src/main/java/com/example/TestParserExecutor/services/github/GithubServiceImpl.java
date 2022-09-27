package com.example.TestParserExecutor.services.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@Slf4j
public class GithubServiceImpl implements GithubService {
    @Value("${github.token}")
    String githubToken;
    @Value("${github.username}")
    String githubUsername;
    @Value("${github.mail}")
    String githubMail;

    @Override
    public boolean createRepo(String repoName) {
        try {
            String[] command = {"curl", "-H", "Authorization: token " + githubToken + "", "--data", "{\"name\":\"" + repoName + "\"}", "https://api.github.com/user/repos"};
            ProcessBuilder p = new ProcessBuilder(command);
            String result = processRunner(p);
            return !result.contains("name already exists on this account");
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    @Override
    public boolean pushCode(String filePath, String repoName) {
        String[] myCommands = {
                "git init",
                "git add -A",
                "git commit -m \"first commit\"",
                "git branch -M master",
                "git push --force https://" + githubToken + "@github.com/" + githubUsername + "/" + repoName
        };
        AtomicBoolean result = new AtomicBoolean(true);
        Arrays.stream(myCommands).sequential().forEach(myCommand -> {
            boolean response = runCommand(myCommand, filePath);
            if (!response) {
                result.set(false);
            }
        });
        return result.get();
    }

    @Override
    public boolean updateCode(String filePath, String repoName) {
        String[] myCommands = {
                "git config core.autocrlf true",
                "git init",
                "git add -A",
                "git config --global user.email \"" + githubMail + "\"",
                "git config --global user.name \"" + githubUsername + "\"",
                "git commit -m \"update\"",
                "git branch -M master",
                "git push --force https://" + githubToken + "@github.com/" + githubUsername + "/" + repoName};
        AtomicBoolean result = new AtomicBoolean(true);
        Arrays.stream(myCommands).sequential().forEach(myCommand -> {
            boolean response = runCommand(myCommand, filePath);
            if (!response) {
                result.set(false);
            }
        });
        return result.get();
    }

    private boolean runCommand(String myCommand, String filePath) {
        String[] args = {"/bin/bash", "-c", myCommand, "with", "args"};
        ProcessBuilder p = new ProcessBuilder(args).directory(new File(filePath));

        try {
            processRunner(p);
            return true;
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    private String processRunner(ProcessBuilder p) {
        try {

            Process process = p.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public String getGithubLink() {
        return "https://github.com/" + githubUsername + "/";
    }
}
