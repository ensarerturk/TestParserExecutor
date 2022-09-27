package com.example.TestParserExecutor.services.crud;

import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.repositories.BuildResultRepository;
import com.example.TestParserExecutor.repositories.JobRepository;
import com.example.TestParserExecutor.services.jenkins.JenkinsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BuildServiceImpl implements BuildService {
    private final BuildResultRepository buildResultRepository;
    private final JobRepository jobRepository;
    private final JenkinsService jenkinsService;

    @Override
    public BuildResult getBuildResult(String jobName, long buildNumber) {
        Optional<Job> jobOptional = jobRepository.getJobByName(jobName);
        if (jobOptional.isEmpty()) {
            return null;
        }
        Optional<BuildResult> buildResult = buildResultRepository.getBuildConsoleOutByBuildNumAndJob(buildNumber, jobOptional.get());
        if (buildResult.isEmpty()) {
            return null;
        }
        return buildResult.get();
    }

    @Override
    public BuildResult getLastBuildResult(String jobName) {
        long buildNumber = jenkinsService.getLastBuildNumber(jobName);
        return getBuildResult(jobName, buildNumber);
    }
}
