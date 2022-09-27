package com.example.TestParserExecutor.controllers;

import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.Job;
import com.example.TestParserExecutor.model.mappers.BuildResultMapper;
import com.example.TestParserExecutor.model.mappers.JobMapper;
import com.example.TestParserExecutor.model.responses.BuildConsoleResponse;
import com.example.TestParserExecutor.model.responses.BuildGetResponse;
import com.example.TestParserExecutor.model.responses.BuildResultUpsertResponse;
import com.example.TestParserExecutor.model.responses.JobGetResponse;
import com.example.TestParserExecutor.services.TestExecutorService;
import com.example.TestParserExecutor.services.crud.BuildService;
import com.example.TestParserExecutor.services.crud.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class Controller {
    private final TestExecutorService testExecutorService;
    private final JobService jobService;
    private final BuildService buildService;
    private final JobMapper jobMapper;
    private final BuildResultMapper buildResultMapper;

    @RequestMapping(value = "/upload/pipeline", method = RequestMethod.POST)
    public ResponseEntity<BuildResultUpsertResponse> uploadPipeline(@RequestPart("file") MultipartFile file) {
        try {
            BuildResult buildResult = testExecutorService.saveFileAndCreateTestPipeline(file);
            BuildResultUpsertResponse response = buildResultMapper.buildUpsertResponse(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/upload/maven", method = RequestMethod.POST)
    public ResponseEntity<BuildResultUpsertResponse> uploadMaven(@RequestPart("file") MultipartFile file) {
        try {
            BuildResult buildResult = testExecutorService.saveFileAndCreateTestMaven(file);
            BuildResultUpsertResponse response = buildResultMapper.buildUpsertResponse(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}", method = RequestMethod.GET)
    public ResponseEntity<JobGetResponse> getJob(@PathVariable("name") String name) {

        try {
            Job job = jobService.getJob(name);
            JobGetResponse response = jobMapper.from(job);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}/{buildNum}", method = RequestMethod.GET)
    public ResponseEntity<BuildGetResponse> getBuild(@PathVariable("name") String name,
                                                     @PathVariable("buildNum") long buildNum) {

        try {
            BuildResult buildResult = buildService.getBuildResult(name, buildNum);
            BuildGetResponse response = buildResultMapper.buildFrom(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}/{buildNum}/console", method = RequestMethod.GET)
    public ResponseEntity<BuildConsoleResponse> getBuildConsole(@PathVariable("name") String name,
                                                                @PathVariable("buildNum") long buildNum) {

        try {
            BuildResult buildResult = buildService.getBuildResult(name, buildNum);
            BuildConsoleResponse response = buildResultMapper.buildConsoleFrom(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}/last-build", method = RequestMethod.GET)
    public ResponseEntity<BuildGetResponse> getLastBuild(@PathVariable("name") String name) {

        try {
            BuildResult buildResult = buildService.getLastBuildResult(name);
            BuildGetResponse response = buildResultMapper.buildFrom(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}/last-build/console", method = RequestMethod.GET)
    public ResponseEntity<BuildConsoleResponse> getLastBuildConsole(@PathVariable("name") String name) {

        try {
            BuildResult buildResult = buildService.getLastBuildResult(name);
            BuildConsoleResponse response = buildResultMapper.buildConsoleFrom(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/job/{name}/last-build/console/XML", method = RequestMethod.GET)
    public ResponseEntity<BuildConsoleResponse> getLastBuildConsoleXML(@PathVariable("name") String name) {

        try {
            BuildResult buildResult = buildService.getLastBuildResult(name);
            BuildConsoleResponse response = buildResultMapper.buildConsoleFrom(buildResult);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

