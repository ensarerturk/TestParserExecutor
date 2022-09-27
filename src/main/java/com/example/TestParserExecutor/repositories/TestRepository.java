package com.example.TestParserExecutor.repositories;

import com.example.TestParserExecutor.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
