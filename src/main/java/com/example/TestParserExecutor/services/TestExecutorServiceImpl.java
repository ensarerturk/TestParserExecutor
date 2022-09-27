package com.example.TestParserExecutor.services;

import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.repositories.JobRepository;
import com.example.TestParserExecutor.services.file.UploadService;
import com.example.TestParserExecutor.services.file.ZipService;
import com.example.TestParserExecutor.services.github.GithubService;
import com.example.TestParserExecutor.services.jenkins.JenkinsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class TestExecutorServiceImpl implements TestExecutorService {
    private final UploadService uploadService;
    private final ZipService zipService;
    private final GithubService githubService;
    private final JenkinsService jenkinsService;
    private final JobRepository jobRepository;

    public TestExecutorServiceImpl(UploadService uploadService, ZipService zipService, GithubService githubService, JenkinsService jenkinsService, JobRepository jobRepository) {
        this.uploadService = uploadService;
        this.zipService = zipService;
        this.githubService = githubService;
        this.jenkinsService = jenkinsService;
        this.jobRepository = jobRepository;
    }

    @Override
    public BuildResult saveFileAndCreateTestPipeline(MultipartFile file) throws RuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new RuntimeException();
        }

        String folderPath = System.getProperty("user.dir") + File.separator;
        String filePath = folderPath + fileName;

        boolean result = uploadService.saveFile(file, filePath);
        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        result = zipService.unzipFile(filePath, folderPath);
        if (!result) {
            throw new RuntimeException();
        }

        String repoName = fileName.replace(".zip", "");
        result = githubService.createRepo(repoName);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        if (result) {
            result = githubService.pushCode(filePath.replace(".zip", ""), repoName);
        } else {
            result = githubService.updateCode(filePath.replace(".zip", ""), repoName);
        }

        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        result = jenkinsService.createJobPipeline(repoName, githubService.getGithubLink() + repoName + ".git");
        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        int buildNumber = jenkinsService.getLastBuildNumber(repoName) + 1;

        result = jenkinsService.runJob(repoName);

        if (!result) {
            throw new RuntimeException();
        }

        BuildResult buildResult = jenkinsService.getBuildResult(repoName, buildNumber);

        Optional<Job> jobOptional = jobRepository.getJobByName(repoName);
        Job job = new Job();
        if (jobOptional.isEmpty()) {
            job.setName(repoName);
            job.setBuildResult(new ArrayList<>() {{
                add(buildResult);
            }});
        } else {
            job = jobOptional.get();
            job.getBuildResult().add(buildResult);
        }
        buildResult.setJob(job);
        jobRepository.save(job);

        return buildResult;
    }

    @Override
    public BuildResult saveFileAndCreateTestMaven(MultipartFile file) throws RuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new RuntimeException();
        }

        String folderPath = System.getProperty("user.dir") + File.separator;
        String filePath = folderPath + fileName;

        boolean result = uploadService.saveFile(file, filePath);
        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        result = zipService.unzipFile(filePath, folderPath);
        if (!result) {
            throw new RuntimeException();
        }

        String repoName = fileName.replace(".zip", "");
        result = githubService.createRepo(repoName);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        if (result) {
            result = githubService.pushCode(filePath.replace(".zip", ""), repoName);
        } else {
            result = githubService.updateCode(filePath.replace(".zip", ""), repoName);
        }

        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        result = jenkinsService.createJobMaven(repoName, githubService.getGithubLink() + repoName + ".git");
        if (!result) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        int buildNumber = jenkinsService.getLastBuildNumber(repoName) + 1;

        result = jenkinsService.runJob(repoName);

        if (!result) {
            throw new RuntimeException();
        }

        BuildResult buildResult = jenkinsService.getBuildResult(repoName, buildNumber);

        Optional<Job> jobOptional = jobRepository.getJobByName(repoName);
        Job job = new Job();
        if (jobOptional.isEmpty()) {
            job.setName(repoName);
            job.setBuildResult(new ArrayList<>() {{
                add(buildResult);
            }});
        } else {
            job = jobOptional.get();
            job.getBuildResult().add(buildResult);
        }
        buildResult.setJob(job);
        jobRepository.save(job);

        return buildResult;
    }
}
