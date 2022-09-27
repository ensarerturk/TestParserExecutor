package com.example.TestParserExecutor.pipelines;

public class UnitTestPipeline {
    public static String getXML(String repoLink, String jenkinsCredentials) {
        return "<?xml version='1.1' encoding='UTF-8'?>" +
                "<flow-definition plugin=\"workflow-job@1182.v60a_e6279b_579\">" +
                "    <actions>" +
                "        <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin=\"pipeline-model-definition@2.2086.v12b_420f036e5\"/>" +
                "        <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin=\"pipeline-model-definition@2.2086.v12b_420f036e5\">" +
                "            <jobProperties/>" +
                "            <triggers/>" +
                "            <parameters/>" +
                "            <options/>" +
                "        </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>" +
                "    </actions>" +
                "    <description></description>" +
                "    <keepDependencies>false</keepDependencies>" +
                "    <properties/>" +
                "    <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition\" plugin=\"workflow-cps@2706.v71dd22b_c5a_a_2\">" +
                "        <scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@4.11.3\">" +
                "            <configVersion>2</configVersion>" +
                "            <userRemoteConfigs>" +
                "                <hudson.plugins.git.UserRemoteConfig>" +
                "                    <url>" + repoLink + "</url>" +
                "                    <credentialsId>" + jenkinsCredentials + "</credentialsId>" +
                "                </hudson.plugins.git.UserRemoteConfig>" +
                "            </userRemoteConfigs>" +
                "            <branches>" +
                "                <hudson.plugins.git.BranchSpec>" +
                "                    <name>*/master</name>" +
                "                </hudson.plugins.git.BranchSpec>" +
                "            </branches>" +
                "            <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>" +
                "            <submoduleCfg class=\"empty-list\"/>" +
                "            <extensions/>" +
                "        </scm>" +
                "        <scriptPath>JenkinsFile</scriptPath>" +
                "        <lightweight>true</lightweight>" +
                "    </definition>" +
                "    <triggers/>" +
                "    <disabled>false</disabled>" +
                "</flow-definition>";
    }
}
