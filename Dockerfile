## Multi stage build
# Builder
FROM openjdk:11.0-jdk AS builder
LABEL description="Java Application builder"
RUN apt install git
RUN git clone https://github.com/Goorm-Project-Aladin/web.git
WORKDIR web
RUN chmod 700 gradlew
RUN ./gradlew clean build

# Runnig
FROM gcr.io/distroless/java:11
LABEL description="Java Application run image"
ARG JAR_FILE=/build/libs/web-0.0.1-SNAPSHOT.jar
EXPOSE 60434
COPY --from=builder web/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]