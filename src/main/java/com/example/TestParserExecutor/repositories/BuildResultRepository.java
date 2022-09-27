package com.example.TestParserExecutor.repositories;

import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BuildResultRepository extends JpaRepository<BuildResult, Long> {
    Optional<BuildResult> getBuildConsoleOutByBuildNumAndJob(long buildNumber, Job job);
}
