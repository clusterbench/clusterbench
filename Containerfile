# Build stage
FROM eclipse-temurin:25 AS build

WORKDIR /build

# Copy entire source code
COPY . .

# Build the project and install artifacts
RUN ./mvnw clean install --batch-mode --no-transfer-progress --define skipTests

# Runtime stage
# https://quay.io/repository/wildfly/wildfly
FROM quay.io/wildfly/wildfly:39.0.1.Final-2-jdk17

LABEL maintainer="Radoslav Husar <radosoft@gmail.com>"

# Copy the EAR file from build stage
COPY --from=build /build/clusterbench-ee10-ear/target/clusterbench-ee10.ear /opt/jboss/wildfly/standalone/deployments/

USER root
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/deployments/clusterbench-ee10.ear
USER jboss

# Run the standalone-ha.xml server profile by default
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-ha.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
