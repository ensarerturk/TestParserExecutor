package com.example.TestParserExecutor.services.jenkins;

import com.example.TestParserExecutor.model.BuildResult;
import com.example.TestParserExecutor.model.Test;
import com.example.TestParserExecutor.model.enums.TestStatus;
import com.example.TestParserExecutor.pipelines.ApiTestMavenTestNG;
import com.example.TestParserExecutor.pipelines.UnitTestPipeline;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JenkinsServiceImpl implements JenkinsService {
    @Value("${jenkins.username}")
    String jenkinsUsername;
    @Value("${jenkins.password}")
    String jenkinsPassword;
    @Value("${jenkins.credentials}")
    String jenkinsCredentials;
    @Value("${jenkins.url}")
    String jenkinsUrl;

    private final ObjectMapper mapper;

    private static final HttpClient CLIENT = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();

    public JenkinsServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean createJobPipeline(String jobName, String repoLink) {
        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jenkinsUrl + "/createItem?name=" + jobName))
                .POST(HttpRequest.BodyPublishers.ofString(UnitTestPipeline.getXML(repoLink, jenkinsCredentials)))
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/xml")
                .header("Jenkins-Crumb", getCrumb())
                .build();
        try {
            CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean createJobMaven(String jobName, String repoLink) {
        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jenkinsUrl + "/createItem?name=" + jobName))
                .POST(HttpRequest.BodyPublishers.ofString(ApiTestMavenTestNG.getXML(repoLink, jenkinsCredentials)))
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/xml")
                .header("Jenkins-Crumb", getCrumb())
                .build();
        try {
            CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean runJob(String jobName) {
        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jenkinsUrl + "/job/" + jobName + "/build"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/xml")
                .header("Jenkins-Crumb", getCrumb())
                .build();
        try {
            CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public int getLastBuildNumber(String taskName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(jenkinsUrl + "/job/" + taskName + "/api/json"))
                    .GET()
                    .header("Authorization", getAuthHeader())
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Map responseMap = mapper.readValue(response.body(), Map.class);
            Map lastBuildMap = (HashMap) responseMap.get("lastBuild");
            if (responseMap.get("lastBuild") == null) {
                return 0;
            } else return (int) lastBuildMap.get("number");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public BuildResult getBuildResult(String jobName, int buildNumber) {
        boolean isResultNotReady = true;
        BuildResult buildResult = new BuildResult();
        while (isResultNotReady) {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(jenkinsUrl + "/job/" + jobName + "/" + buildNumber + "/testReport/api/json"))
                        .GET()
                        .header("Authorization", getAuthHeader())
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse<String> response = CLIENT.send(request,
                        HttpResponse.BodyHandlers.ofString());
                Map responseMap = mapper.readValue(response.body(), Map.class);

                buildResult.setBuildNum(buildNumber);
                buildResult.setFailedCount((int) responseMap.get("failCount"));
                buildResult.setPassedCount((int) responseMap.get("passCount"));
                buildResult.setSkippedCount((int) responseMap.get("skipCount"));
                List caseList = (List) ((HashMap) ((List) responseMap.get("suites")).get(0)).get("cases");
                buildResult.setTestCount(caseList.size());
                buildResult.setTests(new ArrayList<>());
                caseList.forEach((testCase) -> {
                    Test test = new Test();
                    test.setClassName((String) ((HashMap) testCase).get("className"));
                    test.setName((String) ((HashMap) testCase).get("name"));
                    test.setErrorDetails((String) ((HashMap) testCase).get("errorDetails"));
                    String status = (String) ((HashMap) testCase).get("status");
                    test.setTestStatus(TestStatus.valueOf(status));
                    buildResult.getTests().add(test);
                });
                buildResult.setTestCount(caseList.size());

                HttpRequest consoleRequest = HttpRequest.newBuilder()
                        .uri(URI.create(jenkinsUrl + "/job/" + jobName + "/" + buildNumber + "/consoleText"))
                        .GET()
                        .header("Authorization", getAuthHeader())
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse<String> consoleResponse = CLIENT.send(consoleRequest,
                        HttpResponse.BodyHandlers.ofString());

                buildResult.setConsoleOut(consoleResponse.body());

                isResultNotReady = false;

                Thread.sleep(3000L);
            } catch (IOException | InterruptedException ignored) {
            }
        }
        return buildResult;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String getCrumb() {
        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jenkinsUrl + "/crumbIssuer/api/json"))
                .GET()
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Map responseMap = mapper.readValue(response.body(), Map.class);
            return (String) responseMap.get("crumb");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private String getAuthHeader() {
        String plainCredentials = jenkinsUsername + ":" + jenkinsPassword;
        String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
        return "Basic " + base64Credentials;
    }
}
