package com.example.TestParserExecutor.services.crud;

import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job getJob(String jobName) {
        Optional<Job> job = jobRepository.getJobByName(jobName);
        if (job.isEmpty()) {
            return new Job();
        }
        return job.get();
    }
}
