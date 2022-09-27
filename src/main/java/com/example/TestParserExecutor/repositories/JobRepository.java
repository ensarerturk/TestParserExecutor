package com.example.TestParserExecutor.repositories;

import com.example.TestParserExecutor.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> getJobByName(String name);
}
