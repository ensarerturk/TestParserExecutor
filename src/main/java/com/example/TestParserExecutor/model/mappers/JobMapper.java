package com.example.TestParserExecutor.model.mappers;

import com.example.TestParserExecutor.config.MappersConfig;
import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.model.responses.JobGetResponse;
import org.mapstruct.Mapper;


@Mapper(
        config = MappersConfig.class,
        uses = {BuildResultMapper.class}
)
public interface JobMapper {
    JobGetResponse from(Job job);
}
