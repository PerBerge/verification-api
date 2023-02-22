FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=verification-api-0.0.1-SNAPSHOT.jar

# VOLUME /tmp
# COPY target/*.jar /verification-api.jar
COPY --from=maven /usr/src/app/target/${JAR_FILE} /

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /verification-api-0.0.1-SNAPSHOT.jar ${0} ${@}"]