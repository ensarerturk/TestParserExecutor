package com.example.TestParserExecutor.services.crud;

import com.example.TestParserExecutor.model.BuildResult;
import org.springframework.stereotype.Service;


@Service
public interface BuildService {
    BuildResult getBuildResult(String jobName, long buildNumber);
    BuildResult getLastBuildResult(String jobName);
}
