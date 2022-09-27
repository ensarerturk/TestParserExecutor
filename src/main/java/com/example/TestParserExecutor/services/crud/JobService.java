package com.example.TestParserExecutor.services.crud;

import com.example.TestParserExecutor.model.Job;
import org.springframework.stereotype.Service;


@Service
public interface JobService {
    Job getJob(String jobName);
}
