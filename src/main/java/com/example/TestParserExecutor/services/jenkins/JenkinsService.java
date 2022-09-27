package com.example.TestParserExecutor.services.jenkins;

import com.example.TestParserExecutor.model.BuildResult;
import org.springframework.stereotype.Service;


@Service
public interface JenkinsService {
    String getCrumb();

    boolean createJobPipeline(String jobName, String repoLink);

    boolean createJobMaven(String jobName, String repoLink);

    boolean runJob(String jobName);

    int getLastBuildNumber(String jobName);

    BuildResult getBuildResult(String jobName, int buildNumber);
}
