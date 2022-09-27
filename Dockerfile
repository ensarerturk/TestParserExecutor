FROM openjdk:17-jdk-slim

RUN adduser --system --group --home /opt/app appuser
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl jq git sudo \
    && rm -rf /var/lib/apt/lists/*

ARG JAR_FILE=target/TestParserExecutor*.jar
COPY --chown=appuser:appuser ${JAR_FILE} /opt/app/application.jar

USER appuser
WORKDIR /opt/app
ENTRYPOINT ["java","-jar","/opt/app/application.jar"]
