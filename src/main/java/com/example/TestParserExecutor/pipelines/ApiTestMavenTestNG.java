package com.example.TestParserExecutor.pipelines;

public class ApiTestMavenTestNG {
    public static String getXML(String repoLink, String jenkinsCredentials) {
        return "<?xml version='1.1' encoding='UTF-8'?>" +
                "<maven2-moduleset plugin=\"maven-plugin@3.19\">" +
                "    <actions/>" +
                "    <description></description>" +
                "    <keepDependencies>false</keepDependencies>" +
                "    <properties/>" +
                "    <scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@4.11.3\">" +
                "        <configVersion>2</configVersion>" +
                "        <userRemoteConfigs>" +
                "            <hudson.plugins.git.UserRemoteConfig>" +
                "                <url>" + repoLink + "</url>" +
                "                    <credentialsId>" + jenkinsCredentials + "</credentialsId>" +
                "            </hudson.plugins.git.UserRemoteConfig>" +
                "        </userRemoteConfigs>" +
                "        <branches>" +
                "            <hudson.plugins.git.BranchSpec>" +
                "                <name>*/master</name>" +
                "            </hudson.plugins.git.BranchSpec>" +
                "        </branches>" +
                "        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>" +
                "        <submoduleCfg class=\"empty-list\"/>" +
                "        <extensions/>" +
                "    </scm>" +
                "    <canRoam>true</canRoam>" +
                "    <disabled>false</disabled>" +
                "    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>" +
                "    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>" +
                "    <triggers/>" +
                "    <concurrentBuild>false</concurrentBuild>" +
                "    <aggregatorStyleBuild>true</aggregatorStyleBuild>" +
                "    <incrementalBuild>false</incrementalBuild>" +
                "    <ignoreUpstremChanges>false</ignoreUpstremChanges>" +
                "    <ignoreUnsuccessfulUpstreams>false</ignoreUnsuccessfulUpstreams>" +
                "    <archivingDisabled>false</archivingDisabled>" +
                "    <siteArchivingDisabled>false</siteArchivingDisabled>" +
                "    <fingerprintingDisabled>false</fingerprintingDisabled>" +
                "    <resolveDependencies>false</resolveDependencies>" +
                "    <processPlugins>false</processPlugins>" +
                "    <mavenValidationLevel>-1</mavenValidationLevel>" +
                "    <runHeadless>false</runHeadless>" +
                "    <disableTriggerDownstreamProjects>false</disableTriggerDownstreamProjects>" +
                "    <blockTriggerWhenBuilding>true</blockTriggerWhenBuilding>" +
                "    <settings class=\"jenkins.mvn.DefaultSettingsProvider\"/>" +
                "    <globalSettings class=\"jenkins.mvn.DefaultGlobalSettingsProvider\"/>" +
                "    <reporters/>" +
                "    <publishers/>" +
                "    <buildWrappers/>" +
                "    <prebuilders/>" +
                "    <postbuilders/>" +
                "    <runPostStepsIfResult>" +
                "        <name>FAILURE</name>" +
                "        <ordinal>2</ordinal>" +
                "        <color>RED</color>" +
                "        <completeBuild>true</completeBuild>" +
                "    </runPostStepsIfResult>" +
                "</maven2-moduleset>";
    }
}
