package com.example.TestParserExecutor.model.responses;

import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.model.Test;
import com.example.TestParserExecutor.model.enums.TestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class BuildGetResponse {
    private long buildNum;
    private int testCount;
    private int passedCount;
    private int skippedCount;
    private int failedCount;
    private List<TestBuildResponse> tests;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TestBuildResponse {
        private String className;
        private String name;
        private TestStatus testStatus;
        private String errorDetails;
    }
}
