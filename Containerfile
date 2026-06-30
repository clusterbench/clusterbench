# Build stage
FROM eclipse-temurin:25 AS build

WORKDIR /build

# Install unzip required by Maven Wrapper to download the .zip distribution;
# without it, mvnw downloads .tar.gz which doesn't match distributionSha256Sum
RUN apt-get update && apt-get install -y unzip

# Copy entire source code
COPY . .

# Build only the EE11 EAR and its dependencies
RUN ./mvnw clean install --batch-mode --no-transfer-progress --define skipTests --projects clusterbench-ee11-ejb,clusterbench-ee11-web,clusterbench-ee11-ear --also-make

# Runtime stage
# https://quay.io/repository/wildfly/wildfly
FROM quay.io/wildfly/wildfly:40.0.1.Final-jdk17

LABEL maintainer="Radoslav Husar <radosoft@gmail.com>"

# Copy the EAR file from build stage
COPY --from=build /build/clusterbench-ee11-ear/target/clusterbench-ee11.ear /opt/jboss/wildfly/standalone/deployments/

USER root
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/deployments/clusterbench-ee11.ear
USER jboss

# Run the standalone-ha.xml server profile by default
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-ha.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
