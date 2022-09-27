package com.example.TestParserExecutor.model.mappers;

import com.example.TestParserExecutor.config.MappersConfig;
import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.responses.BuildConsoleResponse;
import com.example.TestParserExecutor.model.responses.BuildGetResponse;
import com.example.TestParserExecutor.model.responses.BuildResultUpsertResponse;
import com.example.TestParserExecutor.model.responses.JobGetResponse;
import org.mapstruct.Mapper;


@Mapper(
        config = MappersConfig.class,
        uses = {TestMapper.class}
)
public interface BuildResultMapper {
    JobGetResponse.BuildJobGetResponse from(BuildResult buildResult);
    BuildConsoleResponse buildConsoleFrom(BuildResult buildResult);
    BuildGetResponse buildFrom(BuildResult buildResult);
    BuildResultUpsertResponse buildUpsertResponse(BuildResult buildResult);
}
