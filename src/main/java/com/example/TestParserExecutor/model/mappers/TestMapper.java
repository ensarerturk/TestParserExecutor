package com.example.TestParserExecutor.model.mappers;

import com.example.TestParserExecutor.config.MappersConfig;
import com.example.TestParserExecutor.model.Test;
import com.example.TestParserExecutor.model.responses.BuildGetResponse;
import com.example.TestParserExecutor.model.responses.JobGetResponse;
import org.mapstruct.Mapper;


@Mapper(
        config = MappersConfig.class
)
public interface TestMapper {
    JobGetResponse.BuildJobGetResponse.TestJobGetResponse from(Test test);

    BuildGetResponse.TestBuildResponse testBuildResponseFrom(Test test);
}
