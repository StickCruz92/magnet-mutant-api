FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/com.mutant.api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
