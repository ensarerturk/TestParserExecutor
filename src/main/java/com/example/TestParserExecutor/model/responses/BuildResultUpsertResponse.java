package com.example.TestParserExecutor.model.responses;

import com.example.TestParserExecutor.model.Test;
import com.example.TestParserExecutor.model.enums.TestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class BuildResultUpsertResponse {
    private long buildNum;
    private int testCount;
    private int passedCount;
    private int skippedCount;
    private int failedCount;
    private List<TestUpsertResponse> tests;
    private String consoleOut;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TestUpsertResponse {
        private String className;
        private String name;
        private TestStatus testStatus;
        private String errorDetails;
    }
}
