# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

# Copy entire source code
COPY . .

# Build the project and install artifacts
RUN ./mvnw clean install --batch-mode --no-transfer-progress --define skipTests

# Runtime stage
FROM quay.io/wildfly/wildfly:39.0.0.Final-jdk17

LABEL maintainer="Radoslav Husar <radosoft@gmail.com>"

# Copy the EAR file from build stage
COPY --from=build /build/clusterbench-ee10-ear/target/clusterbench-ee10.ear /opt/jboss/wildfly/standalone/deployments/

USER root
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/deployments/clusterbench-ee10.ear
USER jboss

# Run the standalone-ha.xml server profile by default
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-ha.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
