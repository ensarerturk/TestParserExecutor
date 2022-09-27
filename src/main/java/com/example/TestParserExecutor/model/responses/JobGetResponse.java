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
public class JobGetResponse {
    private String name;
    private List<BuildJobGetResponse> buildResult;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BuildJobGetResponse {
        private long buildNum;
        private int testCount;
        private int passedCount;
        private int skippedCount;
        private int failedCount;
        private List<TestJobGetResponse> tests;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class TestJobGetResponse {
            private String className;
            private String name;
            private TestStatus testStatus;
            private String errorDetails;
        }
    }
}



