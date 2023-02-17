FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar verification-api.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /verification-api.jar ${0} ${@}"]